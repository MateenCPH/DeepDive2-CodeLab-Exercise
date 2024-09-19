package dat;

import dat.daos.MovieDAO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Movie;
import dat.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        //EntityManagerFactory emf = dat.config.HibernateConfig.getEntityManagerFactory("sp1_movie");

        //System.out.println(MovieService.getMovieById(139));
        //List<MovieDTO> movies = MovieService.getByRating(5.9, 6);
        //movies.forEach(System.out::println);
        //List<MovieDTO> movies = MovieService.getAllMoviesByReleaseDate("2023-09-01");

        //List<MovieDTO> movies = MovieService.getAllDanishMoviesWithActors();
        //movies.forEach(System.out::println);

        EntityManagerFactory emf = dat.config.HibernateConfig.getEntityManagerFactory("sp1_movie");

        MovieDAO movieDAO = new MovieDAO(emf);
        List<MovieDTO> movieDTO = MovieService.getAllDanishMovies();
        EntityManager em = emf.createEntityManager();

        MovieDTO m1 = movieDTO.get(1);

        List<String> gen = m1.getGenreNames();

        for(String genres : gen){
            System.out.println(genres);
        }


        /*

        for(MovieDTO m : movieDTO){
            Movie movie = new Movie(m,em);
            movieDAO.saveMovie(movie);

        }

         */



    }
}