package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.GenreDTO;
import dat.dtos.GenreListResponseDTO;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class MovieService {

    private static final String API_KEY = System.getenv("API_KEY"); // "YOUR TMDb API key
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";

    public static Map<Integer, String> fetchGenres() throws IOException, InterruptedException {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        GenreListResponseDTO genreListResponse = objectMapper.readValue(response.body(), GenreListResponseDTO.class);

        return genreListResponse.getGenres().stream()
                .collect(Collectors.toMap(GenreDTO::getId, GenreDTO::getName));
    }

    public static MovieDTO getMovieById(int id) throws IOException, InterruptedException {
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

        return objectMapper.readValue(jsonString, MovieDTO.class);
    }

    public static List<MovieDTO> getByRating(double ratingOne, double ratingTwo) throws IOException, InterruptedException {
        int page = 1;
        int totalPages;
        List<MovieDTO> allMovies = new LinkedList<>();

        try {
            do {
                StringBuilder url = new StringBuilder("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=")
                        .append(page)
                        .append("&sort_by=popularity.desc&vote_average.gte=")
                        .append(ratingOne)
                        .append("&vote_average.lte=")
                        .append(ratingOne)
                        .append("&api_key=")
                        .append(API_KEY);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url.toString()))
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

                if (movieResponse.getResults() == null || movieResponse.getResults().isEmpty()) {
                    System.out.println("No movies found for this page.");
                    break;  // Stop if no more movies are found
                }

                List<MovieDTO> movies = movieResponse.getResults();
                allMovies.addAll(movies); // Add movies from the current page to the list

                // Update total pages and increment the page counter
                totalPages = movieResponse.getTotalPages();
                page++;

                System.out.printf("Progress: %d%%%n", (int) Math.floor((double) page / totalPages * 100));

            } while (page <= totalPages);

            // Return the complete list of movies after sorting by release date
            return allMovies;
        } catch (IOException | InterruptedException | RuntimeException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<MovieDTO> getAllMoviesByReleaseDate(String releaseDate) throws IOException, InterruptedException {
        int page = 1;
        int totalPages;
        List<MovieDTO> allMovies = new LinkedList<>();

        try {
            do {
                StringBuilder url = new StringBuilder("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=")
                        .append(page)
                        .append("&primary_release_date.gte=")
                        .append(releaseDate)
                        .append("&sort_by=primary_release_date.asc&api_key=")
                        .append(API_KEY);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url.toString()))
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

                if (movieResponse.getResults() == null || movieResponse.getResults().isEmpty()) {
                    System.out.println("No movies found for this page.");
                    break;  // Stop if no more movies are found
                }

                List<MovieDTO> movies = movieResponse.getResults();
                allMovies.addAll(movies); // Add movies from the current page to the list

                // Update total pages and increment the page counter
                totalPages = movieResponse.getTotalPages();
                page++;

                System.out.printf("Progress: %d%%%n", (int) Math.floor((double) page / totalPages * 100));

            } while (page <= totalPages);

            // Return the complete list of movies after sorting by release date
            return allMovies;
        } catch (IOException | InterruptedException | RuntimeException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<MovieDTO> getAllDanishMovies() throws IOException, InterruptedException {
        int page = 1;
        int totalPages;
        List<MovieDTO> allMovies = new LinkedList<>();
        Map<Integer, String> genreMap = fetchGenres();

        try {
            do {
                StringBuilder url = new StringBuilder("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&page=")
                        .append(page)
                        .append("&primary_release_date.gte=2019-09-01&sort_by=primary_release_date.asc&with_origin_country=DK&api_key=")
                        .append(API_KEY);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url.toString()))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new RuntimeException("Failed to fetch movies: HTTP code " + response.statusCode());
                }

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());

                String jsonString = response.body();
                MovieResponseDTO movieResponse = objectMapper.readValue(jsonString, MovieResponseDTO.class);

                if (movieResponse.getResults() == null || movieResponse.getResults().isEmpty()) {
                    System.out.println("No movies found for this page.");
                    break;
                }

                List<MovieDTO> movies = movieResponse.getResults();
                movies.forEach(movie -> movie.setGenreNames(genreMap)); //Set genre names
                allMovies.addAll(movies); // Add movies from the current page to the list

                totalPages = movieResponse.getTotalPages();
                page++;

                System.out.printf("Progress: %d%%%n", (int) Math.floor((double) page / totalPages * 100));

            } while (page <= totalPages);

            return allMovies;
        } catch (IOException | InterruptedException | RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}