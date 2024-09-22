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

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m"; // Resets the color back to default

    public static void main(String[] args) throws IOException, InterruptedException {
        // Set up the database via this method:
         setup();

        // Display all movies in the database
         displayAllMoviesInDB();

        // Display all genres in the database
        // displayAllGenresInDB();

        // Display all movies in the database with a specific genre
        // displayMoviesByGenre("Action");

        // Display all movies in the database with a specific title
        // displayMoviesByTitle("kælling");

        // Display all movies in the database in between two specific ratings
        // displayMoviesByRating(5.9, 6);

        // Display total average rating of all movies in the database
        // Movies with no ratings are not included in the calculation
        displayTotalAverageRatingOfAllMovies();

        // Display the top ten highest rated movies in the database
        displayTopTenHighestRatedMovies();

        // Display the top ten lowest rated movies in the database
        displayTopTenLowestRatedMovies();

        //Display the top ten most popular movies in the database
        displayTopTenMostPopularMovies();
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

    public static void displayAllMoviesInDB() {
        Set<MovieDTO> movieDTOS = movieDAO.getAll();
        System.out.println(ANSI_BLUE + "Movies in the database:" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }

    public static void displayAllGenresInDB() {
        Set<Genre> genres = genreDAO.getAll();
        System.out.println(ANSI_BLUE + "Genres in the database:" + ANSI_RESET);
        genres.forEach(System.out::println);
    }

    public static void displayMoviesByGenre(String genre) {
        List<MovieDTO> movieDTOS = movieDAO.getMoviesByGenre(genre);
        System.out.println(ANSI_BLUE + "Movies with genre " + genre + ":" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }

    public static void displayMoviesByTitle(String search) {
        List<MovieDTO> movieDTOS = movieDAO.getMoviesByTitle(search);
        System.out.println(ANSI_BLUE + "Movies with title containing " + search + ":" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }

    public static void displayMoviesByRating(double minRating, double maxRating) {
        List<MovieDTO> movieDTOS = movieDAO.getMoviesByRating(minRating, maxRating);
        System.out.println(ANSI_BLUE + "Movies with ratings between " + minRating + " and " + maxRating + ":" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }

    public static void displayTotalAverageRatingOfAllMovies() {
        double totalAverageRating = movieDAO.getTotalAverageRatingOfAllMovies();
        System.out.println(ANSI_BLUE + "Total average rating of all movies: " + ANSI_RESET + totalAverageRating);
    }

    public static void displayTopTenHighestRatedMovies() {
        List<MovieDTO> movieDTOS = movieDAO.getTopTenHighestRatedMovies();
        System.out.println(ANSI_BLUE + "Top ten highest rated movies:" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }

    public static void displayTopTenLowestRatedMovies() {
        List<MovieDTO> movieDTOS = movieDAO.getTopTenLowestRatedMovies();
        System.out.println(ANSI_BLUE + "Top ten lowest rated movies:" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }

    public static void displayTopTenMostPopularMovies() {
        List<MovieDTO> movieDTOS = movieDAO.getTopTenMostPopularMovies();
        System.out.println(ANSI_BLUE + "Top ten most popular movies:" + ANSI_RESET);
        movieDTOS.forEach(System.out::println);
    }
}
