package com.example.masterwork.exception.exceptions;

public class RecommendationOwnedByOtherViewerException extends RequestForbiddenResourceException{

  public RecommendationOwnedByOtherViewerException() {
    super("Forbidden operation: recommendation was made by other viewer");
  }

}
