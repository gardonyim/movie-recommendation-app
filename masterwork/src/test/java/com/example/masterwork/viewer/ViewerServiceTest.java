package com.example.masterwork.viewer;

import com.example.masterwork.exception.exceptions.RequestCauseConflictException;
import com.example.masterwork.viewer.model.RegistrationReqDTO;
import com.example.masterwork.viewer.model.RegistrationResDTO;
import com.example.masterwork.viewer.model.Viewer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.masterwork.TestUtils.defaultViewer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ViewerServiceTest {

  @Mock
  ViewerRepository viewerRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  ViewerServiceImpl viewerService;

  @Test
  public void when_createNewViewer_should_returnProperDTO() {
    when(viewerRepository.findFirstByUsername(anyString())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(anyString())).thenReturn("password");
    Viewer viewer = defaultViewer();
    when(viewerRepository.save(any(Viewer.class))).thenReturn(viewer);
    RegistrationReqDTO reqDTO = RegistrationReqDTO.builder()
        .username("testuser")
        .password("password")
        .email("test@test")
        .build();
    RegistrationResDTO expected = new RegistrationResDTO(viewer);

    RegistrationResDTO actual = viewerService.createViewer(reqDTO);

    assertEquals(expected, actual);
  }

  @Test
  public void when_createViewerExistingUsername_should_throwException() {
    when(viewerRepository.findFirstByUsername(anyString())).thenReturn(Optional.of(new Viewer()));
    RegistrationReqDTO reqDTO = RegistrationReqDTO.builder()
        .username("existingtestuser")
        .password("password")
        .email("test@test")
        .build();

    Throwable exception = assertThrows(RequestCauseConflictException.class, () -> {
      viewerService.createViewer(reqDTO);
    });

    assertEquals("Username is already taken", exception.getMessage());
  }

}
