package com.example.masterwork.viewer;

import com.example.masterwork.viewer.model.RegistrationReqDTO;
import com.example.masterwork.viewer.model.RegistrationResDTO;
import com.example.masterwork.viewer.model.Viewer;

import java.util.Optional;

public interface ViewerService {

  RegistrationResDTO createViewer(RegistrationReqDTO reqDTO);

  Optional<Viewer> fetchByUsername(String username);

}
