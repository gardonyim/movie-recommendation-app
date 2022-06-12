package com.example.masterwork.movie.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class MovieReqDTO {

  @NotNull(message = "Title is required")
  private String title;
  @NotNull(message = "Director id is required")
  private Integer directorId;
  @NotNull(message = "List of actor ids is required")
  private List<Integer> actorIdList;
  private Integer releaseYear;
  private Integer length;
  private String genre;

}
