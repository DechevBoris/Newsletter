package com.dechev.newsletter.controller;

import com.dechev.newsletter.model.User;
import com.dechev.newsletter.service.ContentService;
import com.dechev.newsletter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/user/home", method = RequestMethod.GET)
    public ModelAndView userHome() {
        ModelAndView modelAndView = new ModelAndView("user/home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " +
                user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("userMessage", "Content Available Only for Users with USER Role");
        return modelAndView;
    }

    @RequestMapping(value = "/user/listContent", method = RequestMethod.GET)
    public ModelAndView listContentForUser(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/listContents");
        modelAndView.addObject("contents", userService.findAllNonSubscribedContents(principal.getName()));
        return modelAndView;
    }

    @RequestMapping(value = "/user/subscribe/{id}", method = RequestMethod.GET)
    public String subscribe(@PathVariable("id")int id, Principal principal) {
        userService.subscribe(principal.getName(), id);
        return "redirect:/user/listContent";
    }

    @RequestMapping(value = "/user/listSubscriptions", method = RequestMethod.GET)
    public ModelAndView listUserSubscriptions(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("user/listSubscriptions");
        modelAndView.addObject("contents", userService.findAllSubscribedContents(principal.getName()));
        return modelAndView;
    }

    @RequestMapping(value = "/user/content/{id}", method = RequestMethod.GET)
    public ModelAndView showContentForUser(@PathVariable("id")int id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.checkIfUserSubscribed(id, principal.getName())) {
            modelAndView.setViewName("user/content");
            modelAndView.addObject("content", contentService.findById(id));
        }
        else {
            modelAndView.setViewName("user/home");
            modelAndView.addObject("userMessage", "You are not subscribed for this content");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/user/cancelSubscription/{id}", method = RequestMethod.GET)
    public ModelAndView cancelSubscription(@PathVariable("id")int id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.checkIfUserSubscribed(id, principal.getName())) {
            userService.cancelSubscription(id, principal.getName());
            modelAndView.setViewName("user/home");
//            modelAndView.addObject("contents", userService.findAllSubscribedContents(principal.getName()));
        }
        else {
            modelAndView.setViewName("user/home");
            modelAndView.addObject("userMessage", "You could not unsubscribe if you have not already subscribed for this content");
        }
        return modelAndView;
    }
}
