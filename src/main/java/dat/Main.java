package dat;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.config.HibernateConfig;
import dat.daos.PersonDAO;
import dat.entities.Person;
import dat.services.JsonFileReader;
import dat.services.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(MovieService.getMovieById(99));
    }
}