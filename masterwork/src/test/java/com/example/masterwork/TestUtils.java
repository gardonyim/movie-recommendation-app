package com.example.masterwork;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.director.models.Director;
import com.example.masterwork.genre.models.Genre;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;
import com.example.masterwork.viewer.model.Viewer;

import java.util.Collections;

public class TestUtils {

  public static int random(int from, int to) {
    return (int) ((Math.random() * (from - to)) + from);
  }

  public static Actor defaultActor() {
    return testActorBuilder().build();
  }

  public static Director defaultDirector() {
    return testDirectorBuilder().build();
  }

  public static Genre defaultGenre() {
    return testGenreBuilder().build();
  }

  public static Movie defaultMovie() {
    return testMovieBuilder().build();
  }

  public static Recommendation defaultRecommendation() {
    return testRecommendationBuilder().build();
  }

  public static Viewer defaultViewer() {
    return testViewerBuilder().build();
  }

  public static Actor.ActorBuilder testActorBuilder() {
    return Actor.builder()
        .id(random(100, 999))
        .name("Test Actor")
        .movies(Collections.singletonList(new Movie()));
  }

  public static Director.DirectorBuilder testDirectorBuilder() {
    return Director.builder()
        .id(random(100, 999))
        .name("Test Director")
        .movies(Collections.singletonList(new Movie()));
  }

  public static Genre.GenreBuilder testGenreBuilder() {
    return Genre.builder()
        .id(random(100, 999))
        .type("testgenre")
        .movies(Collections.singletonList(new Movie()));
  }

  public static Movie.MovieBuilder testMovieBuilder() {
    return Movie.builder()
        .id(random(100, 999))
        .title("test movie")
        .director(new Director())
        .releaseYear(2022)
        .length(90)
        .cast(Collections.singletonList(new Actor()))
        .recommendations(Collections.singletonList(new Recommendation()))
        .genre(new Genre());
  }

  public static Recommendation.RecommendationBuilder testRecommendationBuilder() {
    return Recommendation.builder()
        .id(random(100, 999))
        .rating(random(1, 10))
        .recommendationText("recommendation text")
        .movie(new Movie());
  }

  public static Viewer.ViewerBuilder testViewerBuilder() {
    return Viewer.builder()
        .id(random(100, 999))
        .username("testuser")
        .password("password")
        .email("test@test")
        .activation("hgjzfttzvztcvbescdcfd")
        .enabled(true)
        .recommendations(Collections.singletonList(new Recommendation()));
  }

}
