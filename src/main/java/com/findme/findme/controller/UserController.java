package com.findme.findme.controller;

import com.findme.findme.DAO.UserDAO;
import com.findme.findme.entity.*;
import com.findme.findme.Exceptions.UserAlreadyAuthorizedException;
import com.findme.findme.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping(path = "/user-registration")
    public String registerUser(@ModelAttribute User user) {
        userDAO.save(user);
        return "redirect:/user/" + user.getId();
    }

    @GetMapping(path = "/user/{userId}")
    public String profile(HttpSession session, Model model, @PathVariable Long userId) {
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user);
        model.addAttribute("isAuthorized", session.getAttribute("user"));

        return "profile";
    }

    @PostMapping(path = "/login")
    public String authorizeUser(HttpSession session, @ModelAttribute Login login){
        if (session.getAttribute("login") != null)
            throw new UserAlreadyAuthorizedException();

        User user = userDAO.findUserByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(UserNotFoundException::new);

        session.setAttribute("isAuthorized", user);
        session.setAttribute("user", user);

        return "redirect:/user/" + user.getId();
    }

    @GetMapping(path = "/logout")
    public String logoutUser(HttpSession session){
        session.removeAttribute("isAuthorized");
        return "index";
    }
}
