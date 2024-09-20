package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreDAOTest {
    private static GenreDAO dao;
    private static EntityManagerFactory emf;
    Genre g1, g2, g3;
    Movie m1, m2;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = new GenreDAO(emf);
    }

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate(); // Clean up Movie
            em.createQuery("DELETE FROM Genre").executeUpdate(); // Clean up Genre


            g1 = Genre.builder()
                    .genreName("Action")
                    .build();

            g2 = Genre.builder()
                    .genreName("Horror")
                    .build();

            em.persist(g1);
            em.persist(g2);
            em.getTransaction().commit();


        }
    }


    @Test
    void getMoviesByGenre() {

    }

    @Test
    void create() {

        Genre g3 = Genre.builder()
                .genreName("Drama")
                .build();
        dao.create(g3);

    }

    @Test
    void update() {

        g2.setGenreName("Thriller");
        Genre updated = dao.update(g2);
        assertNotNull(updated);
        assertEquals("Thriller", updated.getGenreName());

    }

    @Test
    void delete() {

        dao.delete(g1);


    }

    @Test
    void getById() {

       // int au = dao.getById(g1.getId());

    }

    @Test
    void getAll() {
    }
}