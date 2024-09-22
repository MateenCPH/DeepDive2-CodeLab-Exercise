package dat.daos;

import dat.dtos.PersonDTO;
import dat.entities.Movie;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonDAO implements IDAO<Person> {

    private EntityManagerFactory emf;

    public PersonDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Person create(Person person) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        } catch (PersistenceException e) {
            System.out.println("Error with persisting person" + e);
            return null;
        }
    }

    @Override
    public Person update(Person person) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person updatePerson = em.merge(person);
            em.getTransaction().commit();
            return updatePerson;
        } catch (Exception e) {
            System.out.println("Error could not update person");
            return null;
        }
    }

    @Override
    public void delete(Person person) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error deleting person" + e);
        }
    }

    @Override
    public Person getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Person.class, id);
        } catch (Exception e) {
            System.out.println("Error while getting Persons by Id " + e);
            return null;
        }
    }

    @Override
    public Set<Person> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Person p", Person.class).getResultStream().collect(Collectors.toSet());
        } catch (PersistenceException e) {
            System.out.println("Error while getting Persons list" + e);
            return null;
        }
    }
}