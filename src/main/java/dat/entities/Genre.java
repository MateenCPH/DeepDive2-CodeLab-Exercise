package dat.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "genre_name")
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    // Constructor that accepts a GenreDTO
    public Genre(GenreDTO genreDTO) {
        this.id = genreDTO.getId();
        this.genreName = genreDTO.getGenreName();
    }
}
