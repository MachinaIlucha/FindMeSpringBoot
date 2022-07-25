package com.findme.findme.controller;

import com.findme.findme.DAO.RoleDAO;
import com.findme.findme.DAO.UserDAO;
import com.findme.findme.entity.*;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.service.interfaces.UserService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private final UserDAO userDAO;
    private final UserService userService;

    @Autowired
    public UserController(UserDAO userDAO, UserService userService, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.userService = userService;
    }

    @PostMapping(path = "/user-registration")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/user/" + user.getId();
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/user/{userId}")
    public String profile(Model model, @PathVariable Long userId) {
        User mainUser = SecurityUtil.getAuthorizedUser();

        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user)
                .addAttribute("isAuthorized", mainUser);

        return "profile";
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/myProfile")
    public String myProfile() {
        return "redirect:/user/" + SecurityUtil.getAuthorizedUser().getId();
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/user/{userId}/updateAvatar")
    public String updateAvatar(@RequestParam("file") MultipartFile file, @PathVariable Long userId){
        return userService.changeAvatarOfUserByUserId(file,userId);
    }
}
