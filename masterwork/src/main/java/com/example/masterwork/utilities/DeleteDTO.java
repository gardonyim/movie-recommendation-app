package com.example.masterwork.utilities;

import lombok.Getter;

@Getter
public class DeleteDTO {

  private String status;
  private int id;

  public DeleteDTO(int id) {
    this.status = "deleted";
    this.id = id;
  }

}
