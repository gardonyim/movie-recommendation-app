package com.example.masterwork.movie.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MovieListDTO {

  private List<MovieDTO> movies;

}
