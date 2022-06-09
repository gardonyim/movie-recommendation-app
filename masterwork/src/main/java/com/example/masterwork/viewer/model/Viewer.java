package com.example.masterwork.viewer.model;

import com.example.masterwork.recommendation.models.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "viewers")
public class Viewer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(length = 50)
  private String username;
  private String password;
  private String email;
  private String activation;
  private Boolean enabled;
  @OneToMany
  private List<Recommendation> recommendations;

}
