package com.findme.findme.service.interfaces;

import com.findme.findme.entity.Post;
import com.findme.findme.entity.User;

public interface PostService {

    Post addPost(User user_posted, Long user_page_posted, String message, String location, String users_tagged);
}
