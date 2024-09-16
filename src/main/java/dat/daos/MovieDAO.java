package dat.daos;

import dat.dtos.MovieDTO;

import java.util.Set;

public class MovieDAO implements IDAO<MovieDTO>{
    @Override
    public MovieDTO create(MovieDTO movieDTO) {
        return null;
    }

    @Override
    public MovieDTO update(MovieDTO movieDTO) {
        return null;
    }

    @Override
    public void delete(MovieDTO movieDTO) {

    }

    @Override
    public MovieDTO getById(Long id) {
        return null;
    }

    @Override
    public Set<MovieDTO> getAll() {
        return Set.of();
    }
}
