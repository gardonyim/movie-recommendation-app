package com.example.masterwork.movie.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieReqDTO {

  @NotNull(message = "Title is required")
  private String title;
  @NotNull(message = "Director id is required")
  private Integer directorId;
  @NotNull(message = "List of actor ids is required")
  private List<Integer> actorIdList;
  private Integer releaseYear;
  private Integer length;
  private Integer genreId;

}
