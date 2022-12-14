package com.example.masterwork.director;

import com.example.masterwork.director.models.Director;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends CrudRepository<Director, Integer> {

  @Override
  List<Director> findAll();

  Optional<Director> findFirstByName(String name);

}
