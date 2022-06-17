package com.example.masterwork.genre;

import com.example.masterwork.genre.models.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Integer> {

  Optional<Genre> findByType(String type);

  @Override
  List<Genre> findAll();

}
