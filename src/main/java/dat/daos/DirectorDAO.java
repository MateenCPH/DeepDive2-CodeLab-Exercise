package dat.daos;

import dat.dtos.PersonDTO;

import java.util.Set;

public class DirectorDAO implements IDAO<PersonDTO> {

    @Override
    public PersonDTO create(PersonDTO directorDTO) {
        return null;
    }

    @Override
    public PersonDTO update(PersonDTO directorDTO) {
        return null;
    }

    @Override
    public void delete(PersonDTO directorDTO) {

    }

    @Override
    public PersonDTO getById(Long id) {
        return null;
    }

    @Override
    public Set<PersonDTO> getAll() {
        return Set.of();
    }
}
