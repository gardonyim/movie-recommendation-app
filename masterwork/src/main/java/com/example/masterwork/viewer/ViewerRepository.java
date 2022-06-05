package com.example.masterwork.viewer;

import com.example.masterwork.viewer.model.Viewer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ViewerRepository extends CrudRepository<Viewer, Integer> {

  Optional<Viewer> findFirstByUsername(String username);

}
