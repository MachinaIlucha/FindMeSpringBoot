package com.findme.findme.service.interfaces;

import com.findme.findme.entity.User;

import java.util.List;

public interface UserService {

    List<User> getFriendsOfUserById(Long user_id);

}
