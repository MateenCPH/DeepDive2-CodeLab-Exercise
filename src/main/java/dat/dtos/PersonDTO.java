package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PersonDTO {

    private Long personId;

    private String name;

    @JsonProperty("known_for_department")
    private String role;

    private int gender;

    private String character;

    @JsonProperty("cast_id")
    private int castId;

    /*
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class known_for {
        private String title;
        private String overview;

        @JsonProperty("mediaType")
        private String media_type;

        double popularity;

        @JsonProperty("releaseDate")
        private LocalDate release_date;

        @JsonProperty("originalLanguage")
        private String original_language;

        @JsonProperty("voteAverage")
        private double vote_average;

        @JsonProperty("voteCount")
        private double vote_count;
    }

    */
}
