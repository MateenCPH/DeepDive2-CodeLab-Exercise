package dat.daos;

import dat.dtos.GenreDTO;

import java.util.Set;

public class GenreDAO implements IDAO<GenreDTO> {
    @Override
    public GenreDTO create(GenreDTO genreDTO) {
        return null;
    }

    @Override
    public GenreDTO update(GenreDTO genreDTO) {
        return null;
    }

    @Override
    public void delete(GenreDTO genreDTO) {

    }

    @Override
    public GenreDTO getById(Long id) {
        return null;
    }

    @Override
    public Set<GenreDTO> getAll() {
        return Set.of();
    }
}
