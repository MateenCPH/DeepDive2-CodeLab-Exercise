package dat.daos;

import dat.dtos.DirectorDTO;

import java.util.Set;

public class DirectorDAO implements IDAO<DirectorDTO> {

    @Override
    public DirectorDTO create(DirectorDTO directorDTO) {
        return null;
    }

    @Override
    public DirectorDTO update(DirectorDTO directorDTO) {
        return null;
    }

    @Override
    public void delete(DirectorDTO directorDTO) {

    }

    @Override
    public DirectorDTO getById(Long id) {
        return null;
    }

    @Override
    public Set<DirectorDTO> getAll() {
        return Set.of();
    }
}
