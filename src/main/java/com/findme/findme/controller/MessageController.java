package com.findme.findme.controller;

import com.findme.findme.entity.Message;
import com.findme.findme.service.interfaces.MessageService;
import com.findme.findme.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Message save(@RequestParam String text,
                        @RequestParam Long userToId){

        return messageService.save(text, userToId, SecurityUtil.getAuthorizedUserId());
    }

    @DeleteMapping("/deleteMessages")
    public void deleteMessages(@RequestParam Long[] ids) {
        messageService.delete(ids, SecurityUtil.getAuthorizedUserId());
    }

    @DeleteMapping("/deleteChat")
    public void deleteChat(@RequestParam Long userToId) {
        messageService.deleteChat(userToId, SecurityUtil.getAuthorizedUserId());
    }

    @GetMapping("/chat")
    public List<Message> getChat(@RequestParam Long userToId){

        return messageService.getChat(userToId, SecurityUtil.getAuthorizedUserId());
    }
}
