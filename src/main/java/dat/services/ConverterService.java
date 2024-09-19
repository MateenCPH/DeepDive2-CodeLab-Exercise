package dat.services;

import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.daos.PersonDAO;
import dat.dtos.MovieDTO;
import dat.dtos.PersonDTO;
import dat.entities.MovieCast;
import dat.entities.Movie;
import dat.entities.MovieCast;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.stream.Collectors;
/*


public class ConverterService {

    private EntityManagerFactory emf;

    public ConverterService(EntityManagerFactory emf){
        this.emf = emf;
    }



    public Movie mapMovieDTOToEntity(MovieDTO movieDTO, EntityManager em) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setOriginalTitle(movieDTO.getOriginalTitle());
        movie.setAdult(movieDTO.isAdult());
        movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
        movie.setPopularity(movieDTO.getPopularity());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setVoteAverage(movieDTO.getVoteAverage());

        // Handle Genres
        List<Genre> genres = movieDTO.getGenres().stream()
                .map(genreId -> findOrCreateGenre(genreId, em))
                .collect(Collectors.toList());
        movie.setGenres(genres);

        // Handle Cast
        List<MovieCast> castList = movieDTO.getCast().stream()
        // Handle MovieCast
        List<MovieCast> castList = movieDTO.getCast().stream()
                .map(personDTO -> mapCastDTOToEntity(personDTO, movie, em))
                .collect(Collectors.toList());
        movie.setCast(castList);

        return movie;
    }

    private Genre findOrCreateGenre(Integer genreId, EntityManager em) {
        Genre genre = em.find(Genre.class, genreId);
        if (genre == null) {
            // Fetch genre name from API if needed, or throw an exception
            genre = new Genre();
            genre.setId(genreId);
            genre.setGenreName("Unknown"); // Set proper name if available
            em.persist(genre);
        }
        return genre;
    }

    private MovieCast mapCastDTOToEntity(PersonDTO personDTO, Movie movie, EntityManager em) {
        Person person = findOrCreatePerson(personDTO, em);
        MovieCast cast = new MovieCast();
        cast.setMovie(movie);
        cast.setPerson(person);
        cast.setCharacter(personDTO.getCharacter());

        return cast;
    }

    private Person findOrCreatePerson(PersonDTO personDTO, EntityManager em) {
        Person person = em.find(Person.class, personDTO.getPersonId());
        if (person == null) {
            person = new Person();
            person.setName(personDTO.getName());
            person.setRole(personDTO.getRole());
            person.setGender(personDTO.getGender());
            em.persist(person);
        }
        return person;
    }

    public Movie persistMovie(MovieDTO movieDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Movie movie = mapMovieDTOToEntity(movieDTO, em);
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } finally {
            em.close();
        }
    }

    /*public Genre convertGenreToEntity(GenreDTO dto){
        return Genre.builder()
                .id(dto.getId())
                .genreName(dto.getGenreName())
                .build();
    }

    public Movie convertMovieToEntity(MovieDTO dto) {
        Movie.builder()
                .title(dto.getTitle())
                .originalTitle(dto.getOriginalTitle())
                .originalLanguage(dto.getOriginalLanguage())
                .adult(dto.isAdult())
                .popularity(dto.getPopularity())
                .releaseDate(dto.getReleaseDate())
                .voteAverage(dto.getVoteAverage())
                .build();
                 /*

        if(dto.getGenres()!= null && !dto.getGenres().isEmpty()){
            List<Movie> genre = genreDAO.getById(dto.getGenres()
        }


        return null;
    }*/












