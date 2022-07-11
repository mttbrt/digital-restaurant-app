package com.mttbrt.digres.service;

import com.mttbrt.digres.dto.request.AddUserReqDTO;
import com.mttbrt.digres.dto.request.UpdateUserReqDTO;
import com.mttbrt.digres.dto.response.BasicResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.exception.FoundException;
import com.mttbrt.digres.exception.NotFoundException;

public interface UserService {

  ResponseDTO getUsers();

  ResponseDTO getUser(long userId) throws NotFoundException;

  BasicResDTO addUser(AddUserReqDTO request) throws FoundException;

  BasicResDTO updateUser(long userId, UpdateUserReqDTO request) throws NotFoundException;

  BasicResDTO deleteUser(long userId) throws NotFoundException;

}
