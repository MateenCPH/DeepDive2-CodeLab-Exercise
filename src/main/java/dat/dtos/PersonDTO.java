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

    private Long id;
    private boolean adult;
    @JsonProperty("also_known_as")
    private String alsoKnownAs;
    private String biography;
    private Date birthday;
    private Date deathday;
    private int gender;
    @JsonProperty("id")
    private int personId;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    private double popularity;

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
