package com.findme.findme.service;

import com.findme.findme.DAO.PostDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Post;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;
    private final UserDAO userDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO, UserDAO userDAO) {
        this.postDAO = postDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Post addPost(User user_posted_id, Long user_page_posted, String message, String location, String users_tagged) {
        List<User> userList_tagged = new ArrayList<>();
        if (!users_tagged.isEmpty()){
            String[] users = users_tagged.split(" ");

            for (String user_id: users)
                userList_tagged.add(userDAO.findById(Long.parseLong(user_id)).orElseThrow(UserNotFoundException::new));
        }

        Post post = new Post();
        post.setUserPosted(user_posted_id);

        User users_page = userDAO.findById(user_page_posted).orElseThrow(UserNotFoundException::new);
        post.setUserPagePosted(users_page);
        post.setMessage(message);
        post.setLocation(location);
        post.setUsersTagged(userList_tagged);

        return postDAO.save(post);
    }

    @Override
    public List<Post> getPostsByUserPage(Long user_id) {
        User user = userDAO.findById(user_id).orElseThrow(UserNotFoundException::new);
        return postDAO.getPostsByUserPagePostedOrderByDatePostedDesc(user);
    }
}
