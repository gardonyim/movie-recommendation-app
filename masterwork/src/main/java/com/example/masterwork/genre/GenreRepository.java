package com.example.masterwork.genre;

import com.example.masterwork.genre.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
