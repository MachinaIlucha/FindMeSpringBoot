package com.findme.findme.controller;

import com.findme.findme.DAO.RoleDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.entity.*;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class UserController {

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    private final RoleDAO roleDAO;

    @Autowired
    public UserController(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleDAO = roleDAO;
    }

    @PostMapping(path = "/user-registration")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleDAO.findRoleByRoleName(RoleName.USER)));
        userDAO.save(user);
        return "redirect:/user/" + user.getId();
    }

    @GetMapping(path = "/user/{userId}")
    public String profile(Model model, @PathVariable Long userId) {
        User mainUser = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);

        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user);
        model.addAttribute("isAuthorized", mainUser);

        return "profile";
    }

    @GetMapping(path = "/myProfile")
    public String myProfile() {
        User mainUser = userDAO.findById(SecurityUtil.getAuthorizedUserId()).orElseThrow(UserNotFoundException::new);

        return "redirect:/user/" + mainUser.getId();
    }
}
