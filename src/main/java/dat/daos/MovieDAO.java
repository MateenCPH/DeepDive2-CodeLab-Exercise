package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<Movie> {

    private EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;    }


    @Override
    public Movie create(Movie movie) {
        return null;
    }

    @Override
    public Movie update(Movie movie) {
        return null;
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
