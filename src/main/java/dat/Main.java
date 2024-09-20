package dat;

import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        EntityManagerFactory emf = dat.config.HibernateConfig.getEntityManagerFactory("sp1_movie");

        //System.out.println(MovieService.getMovieById(139));
        //List<MovieDTO> movies = MovieService.getByRating(5.9, 6);
        //movies.forEach(System.out::println);
        //List<MovieDTO> movies = MovieService.getAllMoviesByReleaseDate("2023-09-01");

        //List<MovieDTO> movies = MovieService.getAllDanishMoviesWithActors();
        //movies.forEach(System.out::println);

        //List<MovieDTO> movieDTOS = MovieService.getAllDanishMovies();
        //movieDTOS.forEach(System.out::println);

        GenreDAO genreDAO = new GenreDAO(emf);
        Set<GenreDTO> genreDTOs = MovieService.fetchGenres();
        for (GenreDTO genreDTO : genreDTOs) {
            Genre genre = new Genre(genreDTO);
            genreDAO.create(genre);
        }

        MovieDAO movieDAO = new MovieDAO(emf);
        List<MovieDTO> movieDTO = MovieService.getAllDanishMovies();
        EntityManager em = emf.createEntityManager();

        for (MovieDTO m : movieDTO) {
            movieDAO.saveMovie(m);
        }


//        MovieDTO m1 = movieDTO.get(1);
//
//        List<String> gen = m1.getGenreNames();
//
//        for(String genres : gen){
//            System.out.println(genres);
//        }
    }
}
