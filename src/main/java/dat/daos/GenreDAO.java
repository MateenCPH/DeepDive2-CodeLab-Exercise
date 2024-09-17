package dat.daos;

import dat.dtos.GenreDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreDAO implements IDAO<Genre> {

    private EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public List<Movie> getMoviesByGenre(Long genreId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId", Movie.class);
            query.setParameter("genreId", genreId);
            return query.getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error while retrieving movies by genre: " + e);
            return null;
        }
    }

    @Override
    public Genre create(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            Genre existingGenre = null;

            try {
                // Attempt to find the genre by name
                existingGenre = em.createQuery("SELECT g FROM Genre g WHERE g.name = :name", Genre.class).setParameter("name", genre.getName()).getSingleResult();
            } catch (NoResultException e) {
                // Genre does not exist
            }

            if (existingGenre != null) {
                // update its details
                em.getTransaction().begin();
                existingGenre.setName(genre.getName());  // Update other fields as needed
                em.merge(existingGenre);
                em.getTransaction().commit();
                return existingGenre;
            } else {
                // create a new genre
                em.getTransaction().begin();
                em.persist(genre);
                em.getTransaction().commit();
                return genre;
            }
        } catch (PersistenceException e) {
            System.out.println("Error while updating genre: " + e);
            return null;
        }
    }

    @Override
    public Genre update(Genre genre) {
        try(EntityManager em = emf.createEntityManager()){
            Genre genreFound = em.find(Genre.class, genre.getId());
            em.getTransaction().begin();

            if(genreFound.getName() != null){
                genreFound.setName(genre.getName());
            }
            em.getTransaction().commit();
            return genreFound;
        } catch (PersistenceException e){
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
            return;
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

}
