package org.GODevelopment.SimpleWebApp.Controllers;

import org.GODevelopment.SimpleWebApp.EntityModel.Message;
import org.GODevelopment.SimpleWebApp.EntityModel.User;
import org.GODevelopment.SimpleWebApp.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller // This means that this class is a Controller
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String getGreeting(
            Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String getMain(Map<String, Object> model) {
        Iterable<Message> allMessages = messageRepo.findAll();
        model.put("messages", allMessages);
        return "main";
    }

    @PostMapping("/main") // <form method="post">
    public String addMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String text, //  <input type="text" name="text"
            @RequestParam String tag,  //  <input type="text" name="tag"
            Map<String, Object> model) {

        Message message = new Message(text, tag, user);
        messageRepo.save(message);

        Iterable<Message> allMessages = messageRepo.findAll();
        model.put("messages", allMessages);

        return "main";
    }

    @PostMapping("filter") // <form method="post" action="filter">
    public String getFilterMessages(@RequestParam String filter, // <input type="text" name="filter"
                                    Map<String,Object> model) {
        Iterable<Message> messages;

        if (filter !=null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);

        return "main";
    }

}
