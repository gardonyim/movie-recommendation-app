package com.example.masterwork.genre;

import com.example.masterwork.exception.exceptions.GenreNotFoundException;
import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.genre.models.Genre;
import com.example.masterwork.genre.models.GenreDTO;
import com.example.masterwork.genre.models.GenreListDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static com.example.masterwork.TestUtils.defaultGenre;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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

  @Test
  public void when_addGenreValidType_should_returnProperDTO() {
    when(genreRepository.findByType(anyString())).thenReturn(Optional.empty());
    when(genreRepository.save(any(Genre.class))).then(returnsFirstArg());
    GenreDTO genreDTO = new GenreDTO();
    genreDTO.setType("new genre");

    GenreDTO actual = genreService.addGenre(genreDTO);

    assertEquals("new genre", actual.getType());
  }

  @Test
  public void when_addGenreExistingType_should_throwException() {
    when(genreRepository.findByType(anyString())).thenReturn(Optional.of(new Genre()));
    GenreDTO genreDTO = new GenreDTO();
    genreDTO.setType("existing genre");

    Throwable exception = assertThrows(RequestCauseConflictException.class, () -> {
      GenreDTO actual = genreService.addGenre(genreDTO);
    });

    assertEquals("Genre is already present in the database", exception.getMessage());
  }

  @Test
  public void when_fetchAllGenres_should_returnProperListDTO() {
    when(genreRepository.findAll()).thenReturn(Collections.singletonList(defaultGenre()));

    GenreListDTO actual = genreService.fetchAllGenres();

    assertEquals(1, actual.getGenres().size());
    assertNotNull(actual.getGenres().get(0).getId());
    assertEquals(defaultGenre().getType(), actual.getGenres().get(0).getType());
  }

}
