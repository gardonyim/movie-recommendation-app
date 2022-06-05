package com.example.masterwork.viewer;

import com.example.masterwork.viewer.model.RegistrationReqDTO;
import com.example.masterwork.viewer.model.RegistrationResDTO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewerController {

  private ViewerService viewerService;

  @Autowired
  public ViewerController(ViewerService viewerService) {
    this.viewerService = viewerService;
  }

  @PostMapping("/register")
  public ResponseEntity<RegistrationResDTO> createViewer(@Valid @RequestBody RegistrationReqDTO reqDTO) {
    return ResponseEntity.status(201).body(viewerService.createViewer(reqDTO));
  }

}
