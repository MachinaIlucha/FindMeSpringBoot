package com.findme.findme.controller;

import com.findme.findme.entity.User;
import com.findme.findme.service.interfaces.RelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class FriendsRequestController {

    private final RelationShipService relationShipService;

    @Autowired
    public FriendsRequestController(RelationShipService relationShipService) {
        this.relationShipService = relationShipService;
    }

    @PostMapping(path = "user/sendFriendRequest")
    public String sendFriendRequest(HttpSession session, @RequestParam("id") Long idTo) {
        if (session.getAttribute("isAuthorized") == null)
            return "redirect:/login";

        relationShipService.sendFriendRequest(session, idTo);

        return "redirect:/user/" + idTo;
    }

    @PostMapping(path = "/addFriend")
    public String addFriend(HttpSession session, @RequestParam("id") Long idTo) {
        User userFrom = (User) session.getAttribute("user");

        relationShipService.addFriend(userFrom.getId(), idTo);

        return "redirect:/myProfile";
    }
}
