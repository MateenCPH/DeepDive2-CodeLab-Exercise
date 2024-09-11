package dat;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.HibernateConfig;
import dat.daos.PersonDAO;
import dat.dtos.MovieDTO;
import dat.entities.Person;
import dat.services.JsonFileReader;
import dat.services.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(MovieService.getMovieById(139));
        //List<MovieDTO> movies = MovieService.getByRating(5, 9);
        //movies.forEach(System.out::println);
        System.out.println(MovieService.getMoviesByReleaseDate("2021-09-01"));
    }
}