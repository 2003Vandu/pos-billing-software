package com.eComm.eComm.io;
/*
 *  @ when user try to regrster or creat a id
 * hear we add one to One Mappin one user has only one role
 * */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest
{
      private String name;
      private String email;
      private String password;
      private String role;
}
//3:31:22