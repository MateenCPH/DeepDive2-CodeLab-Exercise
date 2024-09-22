package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GenreDAOTest {
    private static GenreDAO dao;
    private static EntityManagerFactory emf;
    Genre g1, g2;
    MovieDTO m1;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = new GenreDAO(emf);
    }

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createQuery("DELETE FROM Genre").executeUpdate();

            g1 = Genre.builder()
                    .name("Action")
                    .build();

            g2 = Genre.builder()
                    .name("Horror")
                    .build();

            em.persist(g1);
            em.persist(g2);
            em.getTransaction().commit();
        }

        ArrayList<Long> genreIdList = new ArrayList<>();
        genreIdList.add(g1.getId());
        genreIdList.add(g2.getId());

        m1 = new MovieDTO();
        m1.setGenreIds(genreIdList);
    }

    @Test
    @DisplayName("Test create Genre")
    void create() {
        Genre g3 = Genre.builder()
                .name("Drama")
                .build();
        dao.create(g3);
    }

    @Test
    @DisplayName("Test to Update a Genre ")
    void update() {
        g2.setName("Thriller");
        Genre updated = dao.update(g2);
        assertNotNull(updated);
        assertEquals("Thriller", updated.getName());
    }

    @Test
    @DisplayName("Test the delete Genre")
    void delete() {
        int size = dao.getAll().size();
        dao.delete(g1);

        Set<Genre> update = dao.getAll();

        assertEquals(size - 1, update.size());
    }

    @Test
    @DisplayName("Test getting a genre by id")
    void getById() {
        Genre genreFound = dao.getById(g1.getId());
        assertNotNull(genreFound);
        assertEquals("Action", genreFound.getName());
    }

    @Test
    @DisplayName("Test getting all genres")
    void getAll() {
        int ex = 2;
        int au = dao.getAll().size();
        assertEquals(ex, au);
    }

    @Test
    @DisplayName("Test to see if we can get all movies with a genre")
    void TestGetAllGenresPerMovieDTO() {
        List<Genre> genre = dao.getAllGenresPerMovieDTO(m1);

        assertNotNull(genre);
        assertEquals(2, genre.size());
    }
}