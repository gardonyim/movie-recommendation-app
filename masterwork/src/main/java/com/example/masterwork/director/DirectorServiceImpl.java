package com.example.masterwork.director;

import com.example.masterwork.director.models.Director;
import com.example.masterwork.director.models.DirectorDTO;
import com.example.masterwork.director.models.DirectorListDTO;
import com.example.masterwork.exception.exceptions.DirectorNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.movie.models.MovieDTO;
import com.example.masterwork.movie.models.MovieListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {

  private DirectorRepository directorRepository;

  @Autowired
  public DirectorServiceImpl(DirectorRepository directorRepository) {
    this.directorRepository = directorRepository;
  }

  @Override
  public MovieListDTO fetchMoviesByDirector(int id) {
    Director director = directorRepository.findById(id).orElseThrow(DirectorNotFoundException::new);
    return new MovieListDTO(director.getMovies().stream()
        .map(MovieDTO::new)
        .collect(Collectors.toList()));
  }

  @Override
  public DirectorListDTO fetchAllDirectors() {
    return new DirectorListDTO(directorRepository.findAll().stream()
        .map(DirectorDTO::new)
        .collect(Collectors.toList()));
  }

  @Override
  public DirectorDTO addDirector(DirectorDTO directorDTO) {
    if (directorRepository.findFirstByName(directorDTO.getName()).isPresent()) {
      throw new RequestCauseConflictException("Director is already in the database");
    }
    return new DirectorDTO(directorRepository.save(Director.builder().name(directorDTO.getName()).build()));
  }

  @Override
  public Director getDirectorById(int id) {
    return directorRepository.findById(id).orElseThrow(DirectorNotFoundException::new);
  }

//  @Override
//  public Director getDirectorByName(String name) {
//    return directorRepository.findFirstByName(name).orElseThrow(DirectorNotFoundException::new);
//  }

}
