package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    private ResponseEntity<Account> createAccount(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            return new ResponseEntity<Account>(createdAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account createdAccount = accountService.loginUser(account);
        if (createdAccount != null) {
            return new ResponseEntity<>(createdAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    private ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            return new ResponseEntity<Message>(createdMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/messages")
    private ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages/{messageId}")
    private ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return new ResponseEntity<Message>(message, HttpStatus.OK);

    }

    @DeleteMapping("/messages/{messageId}")
    private ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        if (messageService.deleteMessage(messageId)) {
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/messages/{messageId}")
    private ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        boolean updatedMessage = messageService.updateMessage(messageId, message);
        if (updatedMessage) {
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    private ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer accountId) {
        List<Message> userMessages = messageService.getAllMessagesByUser(accountId);
        return new ResponseEntity<List<Message>>(userMessages, HttpStatus.OK);
    }
}
