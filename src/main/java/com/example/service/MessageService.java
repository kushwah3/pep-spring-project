package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) return null;
        if (accountRepository.existsById(message.getPostedBy())) {
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }

        return null;
    }

    public boolean deleteMessage(int id) {
        boolean messageExists = messageRepository.existsById(id);
        if (!messageExists) return false;
        messageRepository.deleteById(id);
        return true;
    }

    public boolean updateMessage(int id, Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) return false;

        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message currMessage = optionalMessage.get();
            currMessage.setMessageText(message.getMessageText());
            messageRepository.save(currMessage);
            return true;
        }

        return false;
    }

    public List<Message> getAllMessagesByUser(int posted_by) {
        List<Message> messageList = messageRepository.findAll();
        List<Message> retList = new ArrayList<Message>();
        for (Message m : messageList) {
            if (m.getPostedBy() == posted_by) {
                retList.add(m);
            }
        }

        return retList;
    }

    

}
