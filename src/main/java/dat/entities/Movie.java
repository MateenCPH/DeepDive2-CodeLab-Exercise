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
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(name = "original_title", nullable = false)
    private String originalTitle;

    @Column(name = "adult_movie", nullable = false)
    private boolean adult;

    @Column(name = "original_language", nullable = false)
    private String originalLanguage;

    private double popularity;
    private LocalDate releaseDate;
    private boolean video;

    @Column(name = "vote_average", nullable = false)
    private double voteAverage;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Cast> cast;

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
}
