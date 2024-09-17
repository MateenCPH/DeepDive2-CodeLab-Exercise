package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "video")
    private boolean video;

    @Column(name = "vote_average", nullable = false)
    private double voteAverage;

    @Column(name = "vote_count", nullable = false)
    private int voteCount;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    //PrePersist and PreUpdate methods
    @Column(name = "created_date_time", nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @ToString.Exclude
    @Column(name = "updated_date_time", nullable = false)
    private LocalDateTime updatedDateTime;

    @PrePersist
    public void prePersist() {
        if (createdDateTime == null) {
            createdDateTime = LocalDateTime.now();
        }
        if (updatedDateTime == null) {
            updatedDateTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedDateTime = LocalDateTime.now();
    }

    @ManyToMany()
    @Column(name = "actors_and_directors")
    private List<Person> persons;
}
