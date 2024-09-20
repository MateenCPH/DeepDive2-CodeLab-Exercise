package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<MovieDTO> {

    private final EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public MovieDTO create(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);
        GenreDAO genreDAO = new GenreDAO(emf);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            movie.setGenres(genreDAO.getAllGenresPerMovieDTO(movieDTO));

            em.persist(movie);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while saving movie: " + e + e.getMessage());
            return null;
        }

        return new MovieDTO(movie);
    }


    @Override
    public MovieDTO update(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);
        try (EntityManager em = emf.createEntityManager()) {
            Movie existingMovie = em.find(Movie.class, movie.getId());

            if (existingMovie == null) {
                System.out.println("Movie with ID " + movie.getId() + " not found.");
                return null;
            }

            // Check if the updated title is unique, excluding the current movie
            String query = "SELECT COUNT(m) FROM Movie m WHERE m.title = :title AND m.id != :id";
            Long count = em.createQuery(query, Long.class)
                    .setParameter("title", movie.getTitle())
                    .setParameter("id", movie.getId())
                    .getSingleResult();

            if (count > 0) {
                System.out.println("A movie with the title '" + movie.getTitle() + "' already exists.");
                return null;
            }

            // Check if updatedMovie has at least one genre
            if (movie.getGenres() == null || movie.getGenres().isEmpty()) {
                System.out.println("Movie must have at least one genre.");
                return null;
            }

            em.getTransaction().begin();

            // Update fields
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setOriginalTitle(movie.getOriginalTitle());
            existingMovie.setAdult(movie.isAdult());
            existingMovie.setOriginalLanguage(movie.getOriginalLanguage());
            existingMovie.setPopularity(movie.getPopularity());
            existingMovie.setReleaseDate(movie.getReleaseDate());
            existingMovie.setVideo(movie.isVideo());
            existingMovie.setVoteAverage(movie.getVoteAverage());
            existingMovie.setGenres(movie.getGenres());
            //existingMovie.setPersons(movie.getPersons());

            MovieDTO movieDTO1 = new MovieDTO(existingMovie);

            em.getTransaction().commit();
            em.close();
            return movieDTO1;
        } catch (PersistenceException e) {
            System.out.println("Error while updating movie: " + e);
            return null;
        }
    }

    @Override
    public void delete(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(movie);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while deleting movie");
        }
    }

    @Override
    public MovieDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie movie = em.find(Movie.class, id);
            return new MovieDTO(movie);
        } catch (PersistenceException e) {
            System.out.println("Error while getting Movie by id");
            return null;
        }
    }

    @Override
    public Set<MovieDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            //Fetch all movies from the database
            List<Movie> movies = em.createQuery("select m FROM Movie m", Movie.class).getResultList();

            //Convert the list of movies to a set of MovieDTOs and collect them in a set
            Set<MovieDTO> movieDTOS = movies.stream()
                    .map(MovieDTO::new)
                    .collect(Collectors.toSet());
            em.close();
            return movieDTOS;
        } catch (PersistenceException e) {
            System.out.println("Error while getting Movie list" + e);
            return null;
        }
    }

    public List<MovieDTO> getMoviesByGenre(String genreName) {
        List<MovieDTO> movieDTOs = new ArrayList<>();

        try (EntityManager em = emf.createEntityManager()) {

            // Retrieve the Genre entity by its name
            TypedQuery<Genre> genreQuery = em.createQuery(
                    "SELECT g FROM Genre g WHERE g.name = :name", Genre.class);
            genreQuery.setParameter("name", genreName);

            Genre genre;
            try {
                genre = genreQuery.getSingleResult();  // Get the genre by name
            } catch (NoResultException e) {
                System.out.println("Genre not found: " + genreName);
                return Collections.emptyList();  // If the genre is not found, return an empty list
            }

            // Retrieve all movies associated with the genre
            TypedQuery<Movie> movieQuery = em.createQuery(
                    "SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId", Movie.class);
            movieQuery.setParameter("genreId", genre.getId());  // Use the found genre's ID


            for (Movie movie : movieQuery.getResultList()) {
                MovieDTO movieDTO = new MovieDTO(movie);
                movieDTOs.add(movieDTO);
            }

        } catch (PersistenceException e) {
            System.out.println("Error while retrieving movies by genre: " + e);
            return null;
        }
        return movieDTOs;
    }

    public List<MovieDTO> getMoviesByRating(double minRating, double maxRating) {
        List<MovieDTO> movieDTOs = new ArrayList<>();

        try (EntityManager em = emf.createEntityManager()) {

            // Retrieve all movies with a rating between minRating and maxRating
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m WHERE m.voteAverage BETWEEN :minRating AND :maxRating", Movie.class);
            query.setParameter("minRating", minRating);
            query.setParameter("maxRating", maxRating);

            for (Movie movie : query.getResultList()) {
                MovieDTO movieDTO = new MovieDTO(movie);
                movieDTOs.add(movieDTO);
            }
        } catch (PersistenceException e) {
            System.out.println("Error while retrieving movies by rating: " + e);
            return null;
        }
        return movieDTOs;
    }

    public List<MovieDTO> getMovieBySearchIgnoringCase(String search) {
        List<MovieDTO> movieDTOs = new ArrayList<>();

        try (EntityManager em = emf.createEntityManager()) {

            // Retrieve all movies with a title that contains the search string, ignoring case
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE :search", Movie.class);
            query.setParameter("search", "%" + search.toLowerCase() + "%");

            for (Movie movie : query.getResultList()) {
                MovieDTO movieDTO = new MovieDTO(movie);
                movieDTOs.add(movieDTO);
            }
        } catch (PersistenceException e) {
            System.out.println("Error while retrieving movies by search: " + e);
            return null;
        }
        return movieDTOs;

    }
}
