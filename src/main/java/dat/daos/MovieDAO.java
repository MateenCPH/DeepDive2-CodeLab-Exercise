package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<Movie> {

    private EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;    }


    /*
    public Set<Movie> createMovies(Set<Movie> movies){

        if(movies.isEmpty()){
            System.out.println("Marcus er lækker, No movies");
            return null;
        }
        for(Movie thisMovie : movies){
            if(thisMovie.getGenres() != null){
                try(EntityManager em = emf.createEntityManager()){
                    em.getTransaction().commit();
                    em.persist(thisMovie);
                    em.getTransaction().commit();
                } catch (PersistenceException e ){
                    System.out.println("Error creating a list of movies" + e );
                    return null;
                }

            } else {
                return null;
            }
        }
        return movies;
    }

     */

    @Override
    public Movie create(Movie movie) {
        // Check if movie has at least one genre
        if (movie.getGenres() == null || movie.getGenres().isEmpty()) {
            System.out.println("Movie must have at least one genre.");
            return null;
        }

        try (EntityManager em = emf.createEntityManager()) {
            // Check if a movie with the same title already exists
            String query = "SELECT COUNT(m) FROM Movie m WHERE m.title = :title";
            Long count = em.createQuery(query, Long.class)
                    .setParameter("title", movie.getTitle())
                    .getSingleResult();

            if (count > 0) {
                System.out.println("A movie with the title '" + movie.getTitle() + "' already exists.");
                return null;
            }

            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } catch (PersistenceException e) {
            System.out.println("Error while creating movie: " + e);
            return null;
        }

    }

    @Override
    public Movie update(Movie movie) {
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
            existingMovie.setPersons(movie.getPersons());

            // The updatedDateTime is automatically updated in the preUpdate method

            em.getTransaction().commit();
            return existingMovie;
        } catch (PersistenceException e) {
            System.out.println("Error while updating movie: " + e);
            return null;
        }
    }
    @Override
    public void delete(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(movie);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while deleting movie");
            return;
        }
    }

    @Override
    public Movie getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, id);
        } catch (PersistenceException e) {
            System.out.println("Error while getting Movie by id");
            return null;
        }
    }

    @Override
    public Set<Movie> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("select m FROM Movie m", Movie.class).getResultStream().collect(Collectors.toSet());
        } catch (PersistenceException e) {
            System.out.println("Error while getting Movie list");
            return null;
        }
    }
}
