package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {
    private static IDAO dao;
    private static EntityManagerFactory emf;
    Movie m1, m2;
    Person p1, p2;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = new MovieDAO(emf);
    }

   @BeforeEach
   void setUp() {
       try(EntityManager em = emf.createEntityManager()){
           Genre genre = new Genre();
           genre.setName("Action");

           m1 = Movie.builder().id(1339624L).title("Free Fall").originalTitle("Frit Fald").adult(false).originalLanguage("da").popularity(1.393).releaseDate(LocalDate.of(2024,8,26)).video(false).voteAverage(0.0).video(false).genres(List.of(genre)).cast(null).build();
           m2 = Movie.builder().id(1353670L).title("Amnesia").originalTitle("Amnesia").adult(false).originalLanguage("da").popularity(5.529).releaseDate(LocalDate.of(2024,9,13)).video(false).voteAverage(9.0).video(false).genres(List.of(genre)).cast(null).build();

           p1 = Person.builder()
                   .name("Lasse Jørgensen")
                   .role("Acting")
                   .gender(2)
                   //.character("Person 2")
                   //.castId(1)
                   .build();

           p2 = Person.builder()
                   .name("Sebastian Poulsen")
                   .role("Acting")
                   .gender(2)
                   //.character()
                   //.castId(2)
                   .build();

           //cast=[PersonDTO(personId=null, name=Lasse Jørgensen, role=Acting, gender=2, character=Person 2, castId=1), PersonDTO(personId=null, name=Sebastian Poulsen, role=Acting, gender=0, character=Person 1, castId=2)], genreNames=[Horror, Thriller, Mystery])

           em.getTransaction().begin();
           em.persist(genre);
           em.createQuery("DELETE FROM Movie").executeUpdate();
           em.createQuery("DELETE FROM Person").executeUpdate();
           em.merge(m1);
           em.merge(m2);
           em.merge(p1);
           em.merge(p2);
           em.getTransaction().commit();
       }
   }

    @AfterAll
    static void tearDown(){

    }

    @Test
    @DisplayName("Test create Movie")
    void create() {
        // Create a Genre object
        Genre genre = new Genre();
        genre.setName("Action");

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(genre);
            em.getTransaction().commit();
        }

        // Build a movie object without setting the ID manually (let Hibernate assign it)
        Movie m = Movie.builder()
                .title("Free Fall 2")
                .originalTitle("Frit Fald 2")
                .adult(false)
                .originalLanguage("da")
                .popularity(1.393)
                .releaseDate(LocalDate.of(2024, 8, 26))
                .video(false)
                .voteAverage(0.0)
                .genres(List.of(genre))
                .cast(null)
                .build();

        // Ensure dao.create returns a Movie object (no need for casting if dao is typed)
        Movie created = (Movie) dao.create(m);

        // Assert the movie was created successfully
        Assertions.assertNotNull(created, "The movie should be created successfully.");
        Assertions.assertNotNull(created.getId(), "The movie should have an ID after creation.");
    }

    @Test
    @DisplayName("Test get Movie by ID")
    void getById() {
        Movie actual = (Movie) dao.getById(m1.getId());

        assertEquals(actual, m1);
    }
}