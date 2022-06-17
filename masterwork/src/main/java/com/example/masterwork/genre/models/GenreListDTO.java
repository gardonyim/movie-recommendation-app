package com.example.masterwork.genre.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GenreListDTO {

  private List<GenreDTO> genres;

}
