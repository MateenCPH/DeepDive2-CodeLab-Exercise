package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adult_person",nullable = false)
    private boolean adult;

    @Column(name = "also_known_as",nullable = false)
    private String alsoKnownAs;

    @Column(name = "biography",length = 2000)
    private String biography;

    @Column(name = "birthday",nullable = false)
    private Date birthday;

    @Column(name ="deathday")
    private Date deathday;

    @Column(name = "gender",nullable = false)
    private int gender;

    @Column(name ="person_id",unique = true,nullable = false)
    private int personId;

    @Column(name ="known_for_department",nullable = false)
    private String knownForDepartment;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name ="place_of_birth",nullable = false)
    private String placeOfBirth;

    @Column(name = "populatity")
    private double popularity;

    @ManyToMany(mappedBy = "persons")
    private List<Movie> movies;

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
