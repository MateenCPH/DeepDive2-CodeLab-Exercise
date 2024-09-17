package dat.daos;

import dat.dtos.GenreDTO;
import dat.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.Set;
import java.util.stream.Collectors;

public class GenreDAO implements IDAO<Genre> {

    private EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Genre create(Genre genre) {
        return null;
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public void delete(Genre genre) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(genre);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            System.out.println("Error deleting Genre" +e);
            return;
        }

    }

    @Override
    public Genre getById(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Genre.class,id);
        } catch (PersistenceException e){
            System.out.println("Error while getting Genre by id" +e);
            return null;
        }
    }

    @Override
    public Set<Genre> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT g FROM Genre g",Genre.class).getResultStream().collect(Collectors.toSet());
        } catch (PersistenceException e){
            System.out.println("Error while getting Genre list" +e);
            return null;
        }
    }
}
