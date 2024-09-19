package dat;

import dat.daos.MovieDAO;
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

        MovieDTO dto = MovieService.getMovieById(611188);

        EntityManager em = emf.createEntityManager();

        Movie movie = new Movie(dto,em);

        movieDAO.saveMovie(movie);


    }
}