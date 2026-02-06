package com.eComm.eComm.Service;

import com.eComm.eComm.io.UserRequest;
import com.eComm.eComm.io.UserResponse;

import java.util.List;

public interface UserService
{
    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);

}
