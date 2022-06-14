package com.example.masterwork.genre;

import com.example.masterwork.exception.exceptions.GenreNotFoundException;
import com.example.masterwork.genre.models.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.masterwork.TestUtils.defaultGenre;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

  @Mock
  GenreRepository genreRepository;

  @InjectMocks
  GenreServiceImpl genreService;

  @Test
  public void when_getGenreByValidId_should_returnGenre() {
    Genre expected = defaultGenre();
    when(genreRepository.findById(anyInt())).thenReturn(Optional.of(expected));

    Genre actual = genreService.getGenreById(1);

    assertEquals(expected, actual);
  }

  @Test
  public void when_getGenreByInvalidId_should_throwException() {
    when(genreRepository.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(GenreNotFoundException.class, () -> {
      genreService.getGenreById(0);
    });
  }

}
