package dat;

import dat.services.MovieService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(MovieService.getMovieById(139));
        //List<MovieDTO> movies = MovieService.getByRating(5, 9);
        //movies.forEach(System.out::println);
        System.out.println(MovieService.getMoviesByReleaseDate("2021-09-01"));
    }
}