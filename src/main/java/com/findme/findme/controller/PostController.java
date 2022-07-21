package com.findme.findme.controller;

import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Post;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.PostService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final UserDAO userDAO;

    @Autowired
    public PostController(PostService postService, UserDAO userDAO) {
        this.postService = postService;
        this.userDAO = userDAO;
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "/user/{userId}/addPost")
    public String addPost(@RequestParam(value = "location") String location,
                          @RequestParam(value = "user_tagged") String users_tagged,
                          @RequestParam("text") String message,
                          @PathVariable Long userId){

        User user = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);;

        postService.addPost(user, userId, message, location, users_tagged);

        return "redirect:/user/" + userId + "/feed";
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/user/{userId}/feed")
    public String userFeed(Model model, @PathVariable Long userId){
        User mainUser = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Post> posts = postService.getPostsByUserPage(user.getId());

        model.addAttribute("user", user)
                .addAttribute("posts", posts)
                .addAttribute("isAuthorized", mainUser);

        return "feed";
    }
}
