package com.findme.findme.service.interfaces;

import com.findme.findme.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<User> getFriendsOfUserById(Long user_id);

    String changeAvatarOfUserByUserId(MultipartFile file, Long user_id);

    User registerUser(User user);

}
