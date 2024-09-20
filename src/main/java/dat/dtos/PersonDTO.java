package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PersonDTO {

    private Long personId;

    private String name;

    @JsonProperty("known_for_department")
    private String role;

    private int gender;

    private String character;

    @JsonProperty("cast_id")
    private int castId;
}