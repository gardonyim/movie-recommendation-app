package com.example.masterwork.viewer;

import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.viewer.model.RegistrationReqDTO;
import com.example.masterwork.viewer.model.RegistrationResDTO;
import com.example.masterwork.viewer.model.Viewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ViewerServiceImpl implements ViewerService {

  private PasswordEncoder passwordEncoder;
  private ViewerRepository viewerRepository;

  @Autowired
  public ViewerServiceImpl(PasswordEncoder passwordEncoder, ViewerRepository viewerRepository) {
    this.passwordEncoder = passwordEncoder;
    this.viewerRepository = viewerRepository;
  }

  @Override
  public RegistrationResDTO createViewer(RegistrationReqDTO reqDTO) {
    validateRegistration(reqDTO);
    Viewer viewer = convert(reqDTO);
    viewer.setEnabled(false);
    viewer.setActivation(generateKey());
    viewer = viewerRepository.save(viewer);
    return new RegistrationResDTO(viewer);
  }

  @Override
  public Optional<Viewer> fetchByUsername(String username) {
    return viewerRepository.findFirstByUsername(username);
  }

  private String generateKey() {
    Random random = new Random();
    char[] word = new char[16];
    for (int j = 0; j < word.length; j++) {
      word[j] = (char) ('a' + random.nextInt(26));
    }
    return new String(word);
  }

  private Viewer convert(RegistrationReqDTO reqDTO) {
    return Viewer.builder()
        .username(reqDTO.getUsername())
        .password(passwordEncoder.encode(reqDTO.getPassword().trim()))
        .email(reqDTO.getEmail())
        .build();
//    Viewer viewer = new Viewer();
//    viewer.setUsername(reqDTO.getUsername());
//    viewer.setPassword(passwordEncoder.encode(reqDTO.getPassword().trim()));
//    viewer.setEmail(reqDTO.getEmail());
//    return viewer;
  }

  private void validateRegistration(RegistrationReqDTO reqDTO) {
    if (viewerRepository.findFirstByUsername(reqDTO.getUsername()).isPresent()) {
      throw new RequestCauseConflictException("Username is already taken");
    }
  }

}
