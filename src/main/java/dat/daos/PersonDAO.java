package dat.daos;

import dat.dtos.PersonDTO;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.Set;
import java.util.stream.Collectors;

public class PersonDAO implements IDAO<Person>{

    private EntityManagerFactory emf;

    public PersonDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Person create(Person person) {
        return null;
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public void delete(Person person) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        } catch (PersistenceException e){
            System.out.println("Error deleting person" + e );
            return;
        }

    }

    @Override
    public Person getById(Long id) {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Person.class, id);
        } catch (Exception e) {
            System.out.println("Error while getting Persons by Id " + e);
            return null;
        }
    }

    @Override
    public Set<Person> getAll() {
       try(EntityManager em = emf.createEntityManager()) {
           return em.createQuery("SELECT p FROM Person p", Person.class).getResultStream().collect(Collectors.toSet());
       } catch (PersistenceException e){
           System.out.println("Error while getting Persons list" + e );
           return null;
       }
    }
}
