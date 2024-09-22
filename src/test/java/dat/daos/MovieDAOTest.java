package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.dtos.PersonDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {
    private static IDAO<MovieDTO> movieDAO;
    private static EntityManagerFactory emf;

    GenreDTO genreDTO1, genreDTO2, genreDTO3;
    MovieDTO movieDTO1, movieDTO2;
    PersonDTO personDTO1, personDTO2;


    List<GenreDTO> genreDTOS;
    List<MovieDTO> movieDTOS;
    List<PersonDTO> personDTOS;


    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        movieDAO = new MovieDAO(emf);
    }

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createQuery("DELETE FROM Person").executeUpdate();
            em.createQuery("DELETE FROM Genre").executeUpdate();

            //Create Genres and add them to an genreDTOS list
            genreDTO1 = new GenreDTO(12L, "Action");
            genreDTO2 = new GenreDTO(14L, "Comedy");
            genreDTO3 = new GenreDTO(16L, "Drama");

            genreDTOS = new ArrayList<>();
            genreDTOS.add(genreDTO1);
            genreDTOS.add(genreDTO2);
            genreDTOS.add(genreDTO3);

            movieDTO1 = new MovieDTO(
                    1L,
                    "Free Fall",
                    "Frit Fald",
                    false,
                    "da",
                    1.393,
                    LocalDate.of(2024, 8, 26),
                    false,
                    0.0,
                    List.of(genreDTO1.getId(), genreDTO2.getId(), genreDTO3.getId()),
                    null);

            movieDTO2 = new MovieDTO(
                    2L,
                    "Amnesia",
                    "Amnesia",
                    false,
                    "da",
                    5.529,
                    LocalDate.of(2024, 9, 13),
                    false,
                    9.0,
                    List.of(genreDTO1.getId(), genreDTO2.getId(), genreDTO3.getId()),
                    null);


            movieDTOS = new ArrayList<>();
            movieDTOS.add(movieDTO1);
            movieDTOS.add(movieDTO2);

            em.getTransaction().commit();


            genreDTOS.stream()
                    .map(Genre::new)
                    .forEach(em::persist);


            movieDTOS.stream()
                    .map(movieDTO -> {
                        Movie movieEntity = new Movie(movieDTO);
                        List<Genre> associatedGenres = new ArrayList<>();

                        // Find genres by ID and associate them with the movie
                        movieDTO.getGenreIds().forEach(genreId -> {
                            Genre genre = em.find(Genre.class, genreId);
                            associatedGenres.add(genre);
                        });

                        movieEntity.setGenres(associatedGenres);
                        return movieEntity;
                    })
                    .forEach(em::persist);


//                        for (GenreDTO genreDTO : genreDTOS) {
//                Genre genre = new Genre(genreDTO);
//                em.persist(genre);
//            }
//
//
//            for (MovieDTO movieDTO : movieDTOS) {
//                Movie movieEntity = new Movie(movieDTO);
//
//                //Prepare to collect associated genres for this movie
//                List<Genre> foundGenres = new ArrayList<>();
//
//                //For each genre ID in the movieDTO (12,14,16), find the corresponding Genre entity
//                for (Long genreId : movieDTO.getGenreIds()) {
//                    Genre foundGenre = em.find(Genre.class, genreId);
//                    if (foundGenre != null) {
//                        foundGenres.add(foundGenre);
//                    } else {
//                        System.out.println("Genre with ID " + genreId);
//                    }
//                }
//
//                //Set the found genres to the Movie entitY
//                movieEntity.setGenres(foundGenres);
//
//                //Persist the movie entity along with its associated genres
//                em.persist(movieEntity);
//            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println("Error in setup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {

    }

    @Test
    @DisplayName("Test create Movie")
    void create() {
        MovieDTO movieDTO = new MovieDTO(
                3L,
                "Free Fall 2",
                "Frit Fald 2",
                false,
                "da",
                1.393,
                LocalDate.of(2024, 8, 26),
                false,
                0.0,
                List.of(genreDTOS.get(0).getId(), genreDTOS.get(1).getId(), genreDTOS.get(2).getId()),
                null);

        MovieDTO expected = movieDAO.create(movieDTO);
        MovieDTO actual = movieDAO.getById(movieDTO.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test get Movie by ID")
    void getById() {
        MovieDTO expected = movieDTOS.get(0);
        MovieDTO actual = movieDAO.getById(expected.getId());
        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Test delete Movie by ID")
    void delete() {
        int numberOf = movieDAO.getAll().size();

        movieDAO.delete(movieDTO1);

        int expected = numberOf - 1;
        int actual = movieDAO.getAll().size();

        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Test Update Movie variables")
    void update() {
        Movie newMovie = Movie.builder()
                .title("Deep Dive")
                .build();
        movieDTO1.setTitle(newMovie.getTitle());
        movieDAO.update(movieDTO1);

        String expected = "Deep Dive";
        String actual = movieDAO.getById(movieDTO1.getId()).getTitle();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test getting all Movies")
    void getAllEmployees() {
        int expected = 2;
        int actual = movieDAO.getAll().size();

        assertEquals(expected, actual);
    }
}