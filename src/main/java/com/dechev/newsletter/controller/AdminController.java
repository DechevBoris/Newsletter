package com.dechev.newsletter.controller;

import com.dechev.newsletter.model.Content;
import com.dechev.newsletter.model.User;
import com.dechev.newsletter.service.ContentService;
import com.dechev.newsletter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("admin/home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " +
                user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/listUsers", method = RequestMethod.GET)
    public ModelAndView listUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/listUsers");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addContent", method = RequestMethod.GET)
    public ModelAndView addContent() {
        ModelAndView modelAndView = new ModelAndView("admin/addContent");
        modelAndView.addObject("content", new Content());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/addContent", method = RequestMethod.POST)
    public ModelAndView confirmAddContent(@ModelAttribute("content") Content content, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("admin/home");
        Content contentExists = contentService.findContentByTitle(content.getTitle());
        if (contentExists != null) {
            bindingResult
                    .rejectValue("title", "error.content",
                            "There is already a content with the title provided");
        }
        else {
            contentService.addContent(content, principal.getName());
            modelAndView.addObject("adminMessage", "Content has been added successfully");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/listContent", method = RequestMethod.GET)
    public ModelAndView listContentForAdmin() {
        ModelAndView modelAndView = new ModelAndView("admin/listContents");
        modelAndView.addObject("contents", contentService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/content/{id}", method = RequestMethod.GET)
    public ModelAndView showContentForAdmin(@PathVariable("id")int id) {
        ModelAndView modelAndView = new ModelAndView("admin/content");
        modelAndView.addObject("content", contentService.findById(id));
        return modelAndView;
    }
}
