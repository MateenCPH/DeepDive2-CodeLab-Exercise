package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Genre;
import dat.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieDTO {

    private Long id;
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
    private List<Long> genreIds = new ArrayList<>();
    @JsonIgnore
    private List<PersonDTO> cast;  // This will be populated later

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.originalTitle = movie.getOriginalTitle();
        this.adult = movie.isAdult();
        this.originalLanguage = movie.getOriginalLanguage();
        this.popularity = movie.getPopularity();
        this.releaseDate = movie.getReleaseDate();
        this.voteAverage = movie.getVoteAverage();
        this.genreIds = movie.getGenres().stream().map(Genre::getId).collect(Collectors.toList()); //Map the genre ids to a list
    }
}