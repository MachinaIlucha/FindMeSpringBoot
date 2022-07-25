package com.findme.findme.controller;

import com.findme.findme.entity.Message;
import com.findme.findme.service.interfaces.MessageService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Secured("ROLE_USER")
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Message save(@RequestParam String text, @RequestParam Long userToId){

        return messageService.save(text, userToId, SecurityUtil.getAuthorizedUser().getId());
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/deleteMessages")
    public void deleteMessages(@RequestParam Long[] ids) {
        messageService.delete(ids, SecurityUtil.getAuthorizedUser().getId());
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/deleteChat")
    public void deleteChat(@RequestParam Long userToId) {
        messageService.deleteChat(userToId, SecurityUtil.getAuthorizedUser().getId());
    }

    @Secured("ROLE_USER")
    @GetMapping("/chat")
    public List<Message> getChat(@RequestParam Long userToId){

        return messageService.getChat(userToId, SecurityUtil.getAuthorizedUser().getId());
    }
}
