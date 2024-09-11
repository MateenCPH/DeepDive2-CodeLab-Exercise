package dat.daos;

import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class PersonDAO implements IDAO<Person> {
    EntityManagerFactory emf;

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

    }

    @Override
    public Person getById(Long id) {
        return null;
    }

    @Override
    public Set<Person> getAll() {
        return Set.of();
    }
}
