package com.example.masterwork.movie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MovieListDTO {

  private List<MovieDTO> movies;

}
