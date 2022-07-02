package com.mttbrt.digres.service;

import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;

public interface UserService {

  ResponseDTO getUsers();

  ResponseDTO addUser(AddUserReqDTO request);

  ResponseDTO getUserByUsername(String username);

  ResponseDTO updateUser(String username, UpdateUserReqDTO request);

  ResponseDTO deleteUser(String username);

}
