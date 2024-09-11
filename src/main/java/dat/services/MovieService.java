package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private static final String API_KEY = System.getenv("API_KEY"); // "YOUR TMDb API key
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";

    public static String getMovieById(int id) throws IOException, InterruptedException {
        String url = BASE_URL + id + "?api_key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = response.body();
        MovieDTO movieDTO = objectMapper.readValue(jsonString, MovieDTO.class);

        return movieDTO.toString();
    }

    public static List<MovieDTO> getByRating(double ratingOne, double ratingTwo) throws IOException, InterruptedException {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&vote_average.gte=" + ratingOne + "&vote_average.lte=" + ratingTwo;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = response.body();

        MovieResponseDTO movieResponse = objectMapper.readValue(jsonString, MovieResponseDTO.class);

        return movieResponse.getResults();
    }

    public static String getMoviesByReleaseDate(String releaseDate) throws IOException, InterruptedException {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&primary_release_date.gte=" + releaseDate;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response is successful
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch movies: HTTP code " + response.statusCode());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonString = response.body();

        MovieResponseDTO movieResponse = objectMapper.readValue(jsonString, MovieResponseDTO.class);

        List<MovieDTO> movies = movieResponse.getResults();
        return movies.stream()
                .filter(movie -> movie.getReleaseDate().isAfter(LocalDate.parse(releaseDate)))
                .sorted(Comparator.comparing(MovieDTO::getReleaseDate)) // Sort by release date (ascending)
                .map(MovieDTO::toString)
                .collect(Collectors.joining("\n"));
    }

}