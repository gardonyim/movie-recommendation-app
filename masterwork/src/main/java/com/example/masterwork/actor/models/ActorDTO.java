package com.example.masterwork.actor.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ActorDTO {

  private int id;
  @NotNull(message = "Name is required")
  private String name;

  public ActorDTO(Actor actor) {
    this.id = actor.getId();
    this.name = actor.getName();
  }
}
