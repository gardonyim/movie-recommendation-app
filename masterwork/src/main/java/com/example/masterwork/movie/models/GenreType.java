package com.example.masterwork.movie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenreType {

  COMEDY("comedy"),
  CRIME("crime"),
  DRAMA("drama"),
  HORROR("horror"),
  ROMANCE("romance"),
  THRILLER("thriller");

  private final String description;

}
