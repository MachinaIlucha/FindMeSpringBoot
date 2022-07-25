package com.findme.findme.controller;

import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Post;
import com.findme.findme.entity.PostFilter;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.PostService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                          @RequestParam("text") String text,
                          @PathVariable Long userId){

        User user = SecurityUtil.getAuthorizedUser();

        postService.addPost(user, userId, text, location, users_tagged);

        return "redirect:/user/" + userId + "/feed";
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/user/{userId}/feed")
    public String userFeed(Model model, @PathVariable Long userId, @RequestBody PostFilter postFilter){
        User mainUser = SecurityUtil.getAuthorizedUser();
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Post> posts = postService.getPostsByUserPage(user.getId(), postFilter);

        model.addAttribute("user", user)
                .addAttribute("posts", posts)
                .addAttribute("isAuthorized", mainUser);

        return "feed";
    }
}
