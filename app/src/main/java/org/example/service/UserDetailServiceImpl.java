package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.UserInfo;
import org.example.model.UserInfoDTO;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailServiceImpl implements UserDetailsService {
   @Autowired
   private final UserRepository userRepository;
   @Autowired
   private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserInfo user= userRepository.findByUsername(username);
        if(user==null){
            throw  new UsernameNotFoundException("could not find the user.....");
        }
        return new CustomUserDetails(user);
    }
    public UserInfo checkIfUserAlreadyExists(UserInfoDTO userInfoDTO){
        return userRepository.findByUsername(userInfoDTO.getUserName());
    }
    public Boolean signupUser(UserInfoDTO userInfoDTO){
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDTO))){
            return false;
        }
        String userId= UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId,userInfoDTO.getUserName(),userInfoDTO.getPassword(),new HashSet<>()));
        return true;
    }
}
