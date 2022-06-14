package com.example.masterwork;

import com.example.masterwork.actor.models.Actor;
import com.example.masterwork.director.models.Director;
import com.example.masterwork.genre.models.Genre;
import com.example.masterwork.movie.models.Movie;
import com.example.masterwork.recommendation.models.Recommendation;

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

  public static Movie defaultMovie() {
    return testMovieBuilder().build();
  }

  public static Recommendation defaultRecommendation() {
    return testRecommendationBuilder().build();
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

}
