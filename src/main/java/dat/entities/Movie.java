package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "original:title", nullable = false)
    private String originalTitle;

    @Column(name = "adult_movie", nullable = false)
    private boolean adult;

    @Column(name = "original_language", nullable = false)
    private String originalLanguage;

    @Column(name = "popularity", nullable = false)
    private double popularity;

    @Column(name = "releaseDate", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "video")
    private boolean video;

    @Column(name = "vote_average", nullable = false)
    private double voteAverage;

    @Column(name = "vote_count", nullable = false)
    private int voteCount;

    @ManyToMany
    @Column(name = "genres")
    private List<Genre> genres;

    @ManyToMany()
    @Column(name = "actors_and_directors")
    private List<Person> persons;
}
