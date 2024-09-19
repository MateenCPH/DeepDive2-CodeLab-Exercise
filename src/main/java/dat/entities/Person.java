package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.PersonDTO;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private int gender;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<MovieCast> cast;

    // Constructor that accepts a PersonDTO
    public Person(PersonDTO personDTO) {
        this.id = personDTO.getPersonId(); // Ensure this matches with the ID mapping you use
        this.name = personDTO.getName();
        this.role = personDTO.getRole();
        this.gender = personDTO.getGender();
    }
}
