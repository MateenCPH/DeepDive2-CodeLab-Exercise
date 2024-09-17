package dat.daos;

import dat.dtos.ActorDTO;

import java.util.Set;

public class ActorDAO implements IDAO<ActorDTO> {

    @Override
    public ActorDTO create(ActorDTO actorDTO) {
        return null;
    }

    @Override
    public ActorDTO update(ActorDTO actorDTO) {
        return null;
    }

    @Override
    public void delete(ActorDTO actorDTO) {
    }

    @Override
    public ActorDTO getById(Long id) {
        return null;
    }

    @Override
    public Set<ActorDTO> getAll() {
        return Set.of();
    }
}
