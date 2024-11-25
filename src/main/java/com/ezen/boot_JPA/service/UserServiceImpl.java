package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.AuthUserDTO;
import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.entity.AuthUser;
import com.ezen.boot_JPA.entity.User;
import com.ezen.boot_JPA.repository.AuthUserRepository;
import com.ezen.boot_JPA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public String join(UserDTO userDTO) {

        String email = userRepository.save(convertUserDTOToUser(userDTO)).getEmail();
        if( email != null ){
            authUserRepository.save(converAuthUserDTOToAuthUser(userDTO));
        }
        return email;
    }

    @Transactional
    @Override
    public UserDTO selectEmail(String username) {
       // findById : ID(PK)를 조건으로 해당 객체를 검색 후 리턴 (Optional)
       // 내가 검색하고자 하는 조건이 ID가 아닐 경우. Repository 등록 후 사용
        Optional<User> optional = userRepository.findById(username);
        List<AuthUser> authUserList = authUserRepository.findByEmail(username);

        if(optional.isPresent()){
            UserDTO userDTO = convertUserToUserDTO(optional.get(),
                    authUserList.stream().map(a -> convertAuthUserToAuthUserDTO(a)).toList()
                    );
            log.info(" login User >>> {} ", userDTO);
            return userDTO;
        }

        return null;
    }

    @Override
    public boolean updateLastLogin(String authEmail) {

        Optional<User> optional = userRepository.findById(authEmail);

        if(optional.isPresent()){
            User user = optional.get();
            user.setLastLogin(LocalDateTime.now());
            String email = userRepository.save(user).getEmail();
            return email == null ? false : true;
        }
        return false;
    }

    // findAll : select * from user;
    @Override
    public List<UserDTO> getList() {

        List<User> userList = userRepository.findAll();

        for( User user : userList){
            if(user.getEmail() != null){
              List<AuthUser> authUserList = authUserRepository.findByEmail(user.getEmail());
              List<AuthUserDTO> authUserDTOList = authUserList.stream().map(a -> convertAuthUserToAuthUserDTO(a)).toList();
              List<UserDTO> userDTOList = userList.stream().map(u -> convertUserToUserDTO(u,authUserDTOList)).toList();
              return userDTOList;
            }
        }
        return null;
    }

    @Override
    public String modifyNoPwd(UserDTO userDTO) {
        log.info(">>> userDTO Email >> {}" , userDTO.getEmail());
        Optional<User> optional = userRepository.findById(userDTO.getEmail());
        log.info( " >>> optional >> {} ", optional.isPresent());
        if(optional.isPresent()){
            User user = optional.get();
            user.setNickName(userDTO.getNickName());
            return userRepository.save(user).getEmail();
        }
        return null;
    }

    @Override
    public String modify(UserDTO userDTO) {
        Optional<User> optional = userRepository.findById(userDTO.getEmail());
        if(optional.isPresent()){
            User user = optional.get();
            user.setNickName(userDTO.getNickName());
            user.setPwd(userDTO.getPwd());
            return userRepository.save(user).getEmail();
        }
        return null;
    }

    @Override
    public void delete(String email) {
        Optional<User> optional = userRepository.findById(email);
        if(optional.isPresent()){
            User user = optional.get();
            List<AuthUser> authUserList = authUserRepository.findByEmail(user.getEmail());
            for(AuthUser authUser : authUserList){
                authUserRepository.deleteById(authUser.getId());
            }
        }
        userRepository.deleteById(email);
    }

}
