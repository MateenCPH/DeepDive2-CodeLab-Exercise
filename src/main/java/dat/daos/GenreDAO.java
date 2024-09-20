package dat.daos;

import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreDAO implements IDAO<Genre> {

    private final EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Genre create(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(genre);
            em.getTransaction().commit();
            return genre;
        } catch (PersistenceException e) {
            System.out.println("Error creating Genre" + e + e.getMessage());
            return null;
        }
    }


    @Override
    public Genre update(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            Genre genreFound = em.find(Genre.class, genre.getId());
            em.getTransaction().begin();

            if (genreFound.getName() != null) {
                genreFound.setName(genre.getName());
            }
            em.getTransaction().commit();
            return genreFound;
        } catch (PersistenceException e) {
            System.out.println("Error updating Genre");
            return null;
        }
    }

    @Override
    public void delete(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(genre);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error deleting Genre" + e);
        }
    }

    @Override
    public Genre getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Genre.class, id);
        } catch (PersistenceException e) {
            System.out.println("Error while getting Genre by id" + e);
            return null;
        }
    }

    @Override
    public Set<Genre> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultStream().collect(Collectors.toSet());
        } catch (PersistenceException e) {
            System.out.println("Error while getting Genre list" + e);
            return null;
        }
    }

    protected List<Genre> getAllGenresPerMovieDTO(MovieDTO movieDTO) {
        // List to hold the Genre objects that will be retrieved from the database
        List<Genre> allGenres = new ArrayList<>();
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            // Loop through each genre ID in the movieDTO object
            for (Long genreId : movieDTO.getGenreIds()) {
                // Create a query to fetch the Genre entity by its ID
                TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g WHERE g.id = :id", Genre.class);
                query.setParameter("id", genreId);
                query.setMaxResults(1);
                try {
                    Genre genre = query.getSingleResult();  // Try to execute the query and retrieve the result
                    allGenres.add(genre);
                } catch (NoResultException e) {
                    String errorMessage = String.format("Genre with ID %d not found. Error: %s", genreId, e.getMessage());
                    throw new EntityNotFoundException(errorMessage, e);
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        // Return the list of all retrieved genres
        return allGenres;
    }
}