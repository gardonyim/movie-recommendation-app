package com.example.masterwork.actor;

import com.example.masterwork.actor.models.Actor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends CrudRepository<Actor, Integer> {

  @Override
  List<Actor> findAll();

  Optional<Actor> findActorByName(String name);

}
