package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private int gender;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<MovieCast> cast;
}