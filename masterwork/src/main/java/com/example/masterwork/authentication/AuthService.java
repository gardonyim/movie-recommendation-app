package com.example.masterwork.authentication;

import com.example.masterwork.authentication.models.LoginDTO;
import com.example.masterwork.authentication.models.TokenDTO;

public interface AuthService {

  TokenDTO authenticate(LoginDTO loginDTO);

}
