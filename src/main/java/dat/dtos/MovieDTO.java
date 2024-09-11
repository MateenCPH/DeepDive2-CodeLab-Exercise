package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieDTO {
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    private boolean adult;
    @JsonProperty("original_language")
    private String originalLanguage;
    private double popularity;
    @JsonProperty("release_date")
    private String releaseDate;
    private boolean video;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;

    @Override
    public String toString() {
        return "MovieDTO{\n" +
                "title='" + title + '\'' + ",\n" +
                "originalTitle='" + originalTitle + '\'' + ",\n" +
                "overview='" + overview + '\'' + ",\n" +
                "adult=" + adult + ",\n" +
                "originalLanguage='" + originalLanguage + '\'' + ",\n" +
                "popularity=" + popularity + ",\n" +
                "releaseDate='" + releaseDate + '\'' + ",\n" +
                "video=" + video + ",\n" +
                "voteAverage=" + voteAverage + ",\n" +
                "voteCount=" + voteCount + "\n" +
                '}';
    }
}


