package dat;

import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;
import dat.entities.Movie;
import dat.services.ConverterService;
import dat.services.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        EntityManagerFactory emf = dat.config.HibernateConfig.getEntityManagerFactory("sp1_movie");


        //System.out.println(MovieService.getMovieById(139));
        //List<MovieDTO> movies = MovieService.getByRating(5.9, 6);
        //movies.forEach(System.out::println);
        //List<MovieDTO> movies = MovieService.getAllMoviesByReleaseDate("2023-09-01");
        List<MovieDTO> movies = MovieService.getAllDanishMoviesWithActors();

        MovieDTO movieDTO = movies.get(0);
        MovieDAO movieDAO = new MovieDAO(emf);
        ConverterService converterService = new ConverterService(emf);
        movieDAO.create(converterService.mapMovieDTOToEntity(movieDTO, emf.createEntityManager()));
    }
}