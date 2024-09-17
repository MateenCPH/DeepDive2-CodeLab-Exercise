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

    private String title;
    private String originalTitle;
    private boolean adult;
    private String originalLanguage;
    private double popularity;
    private LocalDate releaseDate;
    private boolean video;
    private double voteAverage;
    private int voteCount;

    @ManyToMany
    private List<Genre> genres;
}
