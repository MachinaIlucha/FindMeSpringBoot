package com.findme.findme.controller;

import com.findme.findme.DAO.UserDAO;
import com.findme.findme.Exceptions.AddFriendException;
import com.findme.findme.Exceptions.UserNotFoundException;
import com.findme.findme.entity.Relationship;
import com.findme.findme.entity.RelationshipType;
import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.RelationShipService;
import com.findme.findme.service.interfaces.UserService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class RelationshipController {

    private final RelationShipService relationShipService;
    private final UserService userService;
    private final UserDAO userDAO;

    @Autowired
    public RelationshipController(RelationShipService relationShipService, UserService userService, UserDAO userDAO) {
        this.relationShipService = relationShipService;
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/user/{userId}/sendFriendRequest")
    public String sendFriendRequest(@PathVariable Long userId) {
        relationShipService.sendFriendRequest(userId);
        return "redirect:/user/" + userId;
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/user/{userId}/addFriend/{friendId}")
    public String addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        User userFrom = SecurityUtil.getAuthorizedUser();

        if(!Objects.equals(userId, userFrom.getId()))
            throw new AddFriendException();

        relationShipService.addFriend(userFrom.getId(), friendId);

        return "redirect:/user/" + userFrom.getId() + "/friends";
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/user/{userId}/deniedFriend/{friendId}")
    public String deniedFriend(@PathVariable Long friendId, @PathVariable Long userId) {
        relationShipService.deniedFriend(userId, friendId);
        return "redirect:/user/" + userId + "/friends";
    }

    @Secured("ROLE_USER")
    @PostMapping(path = "/user/{userId}/deleteFriend/{friendId}")
    public String deleteFriend(@PathVariable Long friendId, @PathVariable Long userId) {
        relationShipService.deleteFriend(friendId, userId);
        return "redirect:/user/" + userId + "/friends";
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/user/{userId}/friends")
    public String userFriends(Model model, @PathVariable Long userId) {
        User user = userDAO.findById(userId).orElseThrow(UserNotFoundException::new);

        List<User> friends = userService.getFriendsOfUserById(user.getId());

        model.addAttribute("user", user)
                .addAttribute("friends", friends)
                .addAttribute("isAuthorized", SecurityUtil.getAuthorizedUser());

        return "friends";
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/user/{userId}/incomeRequests")
    public String incomeRequests(Model model) {
        User user = SecurityUtil.getAuthorizedUser();

        List<Relationship> incomeRequests = relationShipService.getRelationshipsByUserToIdAndStatus(user.getId(), RelationshipType.WAITING);

        model.addAttribute("user", user)
                .addAttribute("incomeRequests", incomeRequests)
                .addAttribute("isAuthorized", user);

        return "incomeRequests";
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/user/{userId}/outcomeRequests")
    public String outcomeRequests(Model model, @PathVariable Long userId) {
        User user = SecurityUtil.getAuthorizedUser();

        List<Relationship> outcomeRequests = relationShipService.getRelationshipsByUserFromIdAndStatus(userId, RelationshipType.WAITING);

        model.addAttribute("user", user)
                .addAttribute("outcomeRequests", outcomeRequests)
                .addAttribute("isAuthorized", user);

        return "outcomeRequests";
    }


}
