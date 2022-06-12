package com.example.masterwork.movie.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenreTypeConverter implements AttributeConverter<GenreType, String> {

  @Override
  public String convertToDatabaseColumn(GenreType genreType) {
    if (genreType == null) {
      return null;
    }
    return genreType.getDescription();
  }

  @Override
  public GenreType convertToEntityAttribute(String description) {
    if (description == null) {
      return null;
    }

    return Stream.of(GenreType.values())
        .filter(gt -> gt.getDescription().equals(description))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
