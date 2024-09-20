package dat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_cast")
public class MovieCast {
    @Id
    private Long id;

    @ManyToMany
    @JoinColumn(name = "cast_id")
    private List<Movie> movies;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private String character;

    // Ensure there is no duplicate constructor with the same parameter list
    public MovieCast(Movie movies, Person person, String character) {
        this.movies = (List<Movie>) movies;
        this.person = person;
        this.character = character;
    }
}