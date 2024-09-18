package dat.services;

import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.daos.PersonDAO;
import dat.dtos.GenreDTO;
import dat.dtos.GenreListResponseDTO;
import dat.dtos.MovieDTO;
import dat.dtos.PersonDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.entities.Person;

public class Converter {

    private MovieDAO movieDAO;
    private PersonDAO personDAO;
    private GenreDAO genreDAO;

    public Converter(MovieDAO movieDAO, GenreDAO genreDAO, PersonDAO personDAO){
        this.genreDAO = genreDAO;
        this.movieDAO = movieDAO;
        this.personDAO = personDAO;
    }

    public Genre convertGenreToEntity(GenreDTO dto){
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

                  */
        return null;
    }







}
