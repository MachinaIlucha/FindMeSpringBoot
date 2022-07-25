package com.findme.findme.service.interfaces;

import com.findme.findme.entity.Post;
import com.findme.findme.entity.PostFilter;
import com.findme.findme.entity.User;

import java.util.List;

public interface PostService {

    Post addPost(User user_posted, Long user_page_posted, String message, String location, String users_tagged);

    List<Post> getPostsByUserPage(Long user_id, PostFilter postFilter);
}
