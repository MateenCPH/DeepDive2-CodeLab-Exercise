package dat.entities;

import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Long id;
    private String title;
    @Column(name = "original_title")
    private String originalTitle;
    @Column(name = "adult_movie")
    private boolean adult;
    @Column(name = "original_language")
    private String originalLanguage;
    private double popularity;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private boolean video;
    @Column(name = "vote_average")
    private double voteAverage;

    @ManyToMany
    private List<Genre> genres;

    @OneToMany
    private List<MovieCast> cast;

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId(); // Cast int to Long
        this.title = movieDTO.getTitle();
        this.originalTitle = movieDTO.getOriginalTitle();
        this.adult = movieDTO.isAdult();
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.popularity = movieDTO.getPopularity();
        this.releaseDate = movieDTO.getReleaseDate();
        this.video = movieDTO.isVideo();
        this.voteAverage = movieDTO.getVoteAverage();
        this.genres = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return adult == movie.adult && Double.compare(popularity, movie.popularity) == 0 && video == movie.video && Double.compare(voteAverage, movie.voteAverage) == 0 && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(originalTitle, movie.originalTitle) && Objects.equals(originalLanguage, movie.originalLanguage) && Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(genres, movie.genres) && Objects.equals(cast, movie.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, originalTitle, adult, originalLanguage, popularity, releaseDate, video, voteAverage, genres, cast);
    }
}