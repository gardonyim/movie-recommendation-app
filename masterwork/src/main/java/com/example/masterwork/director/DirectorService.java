package com.example.masterwork.director;

import com.example.masterwork.director.models.Director;
import com.example.masterwork.director.models.DirectorDTO;
import com.example.masterwork.director.models.DirectorListDTO;
import com.example.masterwork.movie.models.MovieListDTO;

public interface DirectorService {

  MovieListDTO fetchMoviesByDirector(int id);

  DirectorListDTO fetchAllDirectors();

  DirectorDTO addDirector(DirectorDTO directorDTO);

//  Director getDirectorByName(String name);

}
