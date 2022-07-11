package com.findme.findme.controller;

import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/addPost")
    public String addPost(@RequestParam(value = "location") String location,
                          @RequestParam(value = "user_tagged") String users_tagged,
                          @RequestParam(value = "id") Long profileId,
                          @RequestParam("text") String message,
                          HttpSession session){

        User user = (User) session.getAttribute("user");

        postService.addPost(user, profileId, message, location, users_tagged);

        return "redirect:/user/" + profileId;
    }
}
