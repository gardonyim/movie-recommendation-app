package com.example.masterwork.viewer;

import com.example.masterwork.viewer.model.RegistrationReqDTO;
import com.example.masterwork.viewer.model.RegistrationResDTO;

public interface ViewerService {

  RegistrationResDTO createViewer(RegistrationReqDTO reqDTO);

}
