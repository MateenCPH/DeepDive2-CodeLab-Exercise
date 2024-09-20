package dat;

import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.daos.PersonDAO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import dat.services.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Main {
    private static EntityManagerFactory emf = dat.config.HibernateConfig.getEntityManagerFactory("sp1_movie");
    private static GenreDAO genreDAO = new GenreDAO(emf);
    private static MovieDAO movieDAO = new MovieDAO(emf);
    private static PersonDAO personDAO = new PersonDAO(emf);

    public static void main(String[] args) throws IOException, InterruptedException {

        // Set up the database via this method:
        //setup();

        // Get all movies in the database and print them
        //getAllMoviesInDB();

        // Get all genres in the database and print them
        //getAllGenresInDB();

        // Get all movies in the database with a specific genre and print them
        //getMoviesByGenre("Action");

        // Get all movies in the database with a specific title and print them
        //getMoviesByTitle("kælling");

        // Get all movies in the database in between two specific ratings and print them
        //getMoviesByRating(5.9, 6);
    }

    public static void getAllMoviesInDB() {
        Set<MovieDTO> movieDTOS = movieDAO.getAll();
        movieDTOS.forEach(System.out::println);
    }

    public static void getAllGenresInDB() {
        Set<Genre> genres = genreDAO.getAll();
        genres.forEach(System.out::println);
    }

    public static void getMoviesByGenre(String genre) {
        List<MovieDTO> movieDTOS = movieDAO.getMoviesByGenre(genre);
        movieDTOS.forEach(System.out::println);
    }

    public static void getMoviesByTitle(String search) {
        List<MovieDTO> movieDTOS = movieDAO.getMoviesByTitle(search);
        movieDTOS.forEach(System.out::println);
    }

    public static void getMoviesByRating(double minRating, double maxRating) {
        List<MovieDTO> movieDTOS = movieDAO.getMoviesByRating(minRating, maxRating);
        movieDTOS.forEach(System.out::println);
    }

    public static void setup() {
        try {
            setupGenres();
            setupMovies();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setupGenres() throws IOException, InterruptedException {
        Set<GenreDTO> genreDTOs = MovieService.fetchGenres();
        for (GenreDTO genreDTO : genreDTOs) {
            Genre genre = new Genre(genreDTO);
            genreDAO.create(genre);
        }
    }

    public static void setupMovies() throws IOException, InterruptedException {
        List<MovieDTO> movieDTOs = MovieService.getAllDanishMovies();
        for (MovieDTO movieDTO : movieDTOs) {
            movieDAO.create(movieDTO);
        }
    }
}
