package com.example.masterwork.director;

import com.example.masterwork.director.models.Director;
import com.example.masterwork.exception.exceptions.DirectorNotFoundException;
import com.example.masterwork.movie.MovieService;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {

  private DirectorRepository directorRepository;
  private MovieService movieService;

  @Autowired
  public DirectorServiceImpl(DirectorRepository directorRepository, MovieService movieService) {
    this.directorRepository = directorRepository;
    this.movieService = movieService;
  }

  @Override
  public MovieListDTO fetchMoviesByDirector(int id) {
    Director director = directorRepository.findById(id).orElseThrow(DirectorNotFoundException::new);
    return new MovieListDTO(director.getMovies().stream()
        .map(m -> movieService.convertToDTO(m))
        .collect(Collectors.toList()));
  }
}
