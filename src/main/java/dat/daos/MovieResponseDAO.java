package dat.daos;

import dat.dtos.MovieResponseDTO;

import java.util.Set;

public class MovieResponseDAO implements IDAO<MovieResponseDTO> {

    //Skal måske ikke implements iDAo ?

    @Override
    public MovieResponseDTO create(MovieResponseDTO movieResponseDTO) {
        return null;
    }

    @Override
    public MovieResponseDTO update(MovieResponseDTO movieResponseDTO) {
        return null;
    }

    @Override
    public void delete(MovieResponseDTO movieResponseDTO) {

    }

    @Override
    public MovieResponseDTO getById(Long id) {
        return null;
    }

    @Override
    public Set<MovieResponseDTO> getAll() {
        return Set.of();
    }
}
