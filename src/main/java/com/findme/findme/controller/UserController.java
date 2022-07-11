package com.findme.findme.controller;

import com.findme.findme.DAO.PostDAO;
import com.findme.findme.DAO.RelationshipDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.entity.*;
import com.findme.findme.Exceptions.UserAlreadyAuthorizedException;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private final UserDAO userDAO;
    private final RelationshipDAO relationshipDAO;
    private final UserService userService;
    private final PostDAO postDAO;

    @Autowired
    public UserController(UserDAO userDAO, RelationshipDAO relationshipDAO, UserService userService, PostDAO postDAO) {
        this.userDAO = userDAO;
        this.relationshipDAO = relationshipDAO;
        this.userService = userService;
        this.postDAO = postDAO;
    }

    @PostMapping(path = "/user-registration")
    public String registerUser(@ModelAttribute User user) {
        userDAO.save(user);
        return "redirect:/myProfile";
    }

    @GetMapping(path = "/user/{userId}")
    public String profile(Model model, @PathVariable Long userId) {
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);

        List<User> friends = userService.getFriendsOfUserById(user.getId());
        List<Post> posts = postDAO.getPostsByUserPagePostedId(user.getId());

        model.addAttribute("user", user)
                .addAttribute("friends", friends)
                .addAttribute("posts", posts);

        return "profile";
    }

    @GetMapping(path = "/myProfile")
    public String myProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        List<Relationship> incomeRequests = relationshipDAO.getRelationshipsByUserToIdAndStatus(user.getId(), RelationshipType.WAITING);
        List<Relationship> outcomeRequests = relationshipDAO.getRelationshipsByUserFromIdAndStatus(user.getId(), RelationshipType.WAITING);
        List<User> friends = userService.getFriendsOfUserById(user.getId());
        List<Post> posts = postDAO.getPostsByUserPagePostedId(user.getId());

        model.addAttribute("user", user)
                .addAttribute("incomeRequests", incomeRequests)
                .addAttribute("outcomeRequests", outcomeRequests)
                .addAttribute("friends", friends)
                .addAttribute("posts", posts);

        return "myProfile";
    }

    @PostMapping(path = "/login")
    public String authorizeUser(HttpSession session, @ModelAttribute Login login){
        if (session.getAttribute("login") != null)
            throw new UserAlreadyAuthorizedException();

        User user = userDAO.findUserByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(UserNotFoundException::new);

        session.setAttribute("isAuthorized", login);
        session.setAttribute("user", user);

        return "redirect:/myProfile";
    }

    @GetMapping(path = "/logout")
    public String logoutUser(HttpSession session){
        session.removeAttribute("isAuthorized");
        return "index";
    }
}
