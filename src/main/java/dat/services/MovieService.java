package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.MovieDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieService {

    private static final String API_KEY = System.getenv("API_KEY"); // "YOUR TMDb API key
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static String getMovieById(int id) throws IOException, InterruptedException {
        String url = BASE_URL + id + "?api_key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = response.body();
        MovieDTO movieDTO = objectMapper.readValue(jsonString, MovieDTO.class);
        return movieDTO.toString();
    }
}