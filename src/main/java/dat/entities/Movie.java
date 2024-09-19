package dat.entities;

import dat.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

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
    private List<MovieCast> cast;

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


    public Movie(MovieDTO movieDTO, EntityManager em) {
        this.id = (long) movieDTO.getId(); // Cast int to Long
        this.title = movieDTO.getTitle();
        this.originalTitle = movieDTO.getOriginalTitle();
        this.adult = movieDTO.isAdult();
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.popularity = movieDTO.getPopularity();
        this.releaseDate = movieDTO.getReleaseDate();
        this.video = movieDTO.isVideo();
        this.voteAverage = movieDTO.getVoteAverage();

        // Handle genres, provide an empty list if getGenres() is null
        this.genres = (movieDTO.getGenres() == null ? List.of() : movieDTO.getGenres().stream()
                .map(id -> em.find(Genre.class, id)) // Fetch existing genres from the database
                .filter(Objects::nonNull) // Filter out null values in case any genre IDs are invalid
                .collect(Collectors.toList()));

        // Handle cast (people), provide an empty list if getCast() is null
        this.cast = (movieDTO.getCast() == null ? List.of() : movieDTO.getCast().stream()
                .map(personDTO -> new MovieCast(this, new Person(personDTO), personDTO.getCharacter()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return adult == movie.adult && Double.compare(popularity, movie.popularity) == 0 && video == movie.video && Double.compare(voteAverage, movie.voteAverage) == 0 && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(originalTitle, movie.originalTitle) && Objects.equals(originalLanguage, movie.originalLanguage) && Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(genres, movie.genres) && Objects.equals(cast, movie.cast) && Objects.equals(createdDateTime, movie.createdDateTime) && Objects.equals(updatedDateTime, movie.updatedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, originalTitle, adult, originalLanguage, popularity, releaseDate, video, voteAverage, genres, cast, createdDateTime, updatedDateTime);
    }
}