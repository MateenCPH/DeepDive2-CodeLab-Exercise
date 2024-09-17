package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieDTO {

    private String title;

    @JsonProperty("original_title")
    private String originalTitle;

    private boolean adult;

    @JsonProperty("original_language")
    private String originalLanguage;

    private double popularity;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    private boolean video;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("vote_count")
    private int voteCount;

    @JsonProperty("genre_ids")
    private List<Integer> genres;

    @JsonIgnore
    private List<String> genreNames;  // This will be populated later

    public void setGenreNames(Map<Integer, String> genreMap) {
        if (genres != null) {
            genreNames = genres.stream()
                    .map(genreMap::get)
                    .collect(Collectors.toList());
        }
    }
}