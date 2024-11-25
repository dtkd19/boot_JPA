package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.AuthUserDTO;
import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.entity.AuthUser;
import com.ezen.boot_JPA.entity.User;

import java.util.List;

public interface UserService {

    default User convertUserDTOToUser(UserDTO userDTO){
        return User.builder()
                .email(userDTO.getEmail())
                .pwd(userDTO.getPwd())
                .nickName(userDTO.getNickName())
                .lastLogin(userDTO.getLastLogin())
                .build();
    }

    default AuthUser converAuthUserDTOToAuthUser(UserDTO userDTO){
        return AuthUser.builder()
                .email(userDTO.getEmail())
                .auth("USER_ROLE")
                .build();
    }

    default AuthUserDTO convertAuthUserToAuthUserDTO(AuthUser authUser){
        return AuthUserDTO.builder()
                .email(authUser.getEmail())
                .auth(authUser.getAuth())
                .build();
    }

    default UserDTO convertUserToUserDTO(User user, List<AuthUserDTO> authUserDTOList){
        return  UserDTO.builder()
                .email((user.getEmail()))
                .pwd((user.getPwd()))
                .nickName(user.getNickName())
                .lastLogin((user.getLastLogin()))
                .regAt((user.getRegAt()))
                .modAt((user.getModAt()))
                .authList(authUserDTOList)
                .build();
    }



    String join(UserDTO userDTO);

    UserDTO selectEmail(String username);

    boolean updateLastLogin(String authEmail);

    List<UserDTO> getList();

    String modifyNoPwd(UserDTO userDTO);

    String modify(UserDTO userDTO);

    void delete(String email);
}
