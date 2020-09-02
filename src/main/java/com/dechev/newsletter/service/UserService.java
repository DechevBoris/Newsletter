package com.dechev.newsletter.service;

import com.dechev.newsletter.dto.UserDto;
import com.dechev.newsletter.model.Content;
import com.dechev.newsletter.model.Role;
import com.dechev.newsletter.model.Subscription;
import com.dechev.newsletter.model.User;
import com.dechev.newsletter.repository.RoleRepository;
import com.dechev.newsletter.repository.SubscriptionRepository;
import com.dechev.newsletter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service("userService")
public class UserService {

    @Autowired
    private ContentService contentService;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SubscriptionRepository subscriptionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       SubscriptionRepository subscriptionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        return user;
    }

    public boolean checkIfPasswordsMatch(UserDto userDto) {
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void subscribe(String userEmail, int contentId) {
        Subscription subscription = new Subscription();
        subscription.setUser(findUserByEmail(userEmail));
        subscription.setContent(contentService.findById(contentId));
        subscription.setDateOfSubscription(LocalDateTime.now());
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> findAllSubscription(String email) {
        User user = findUserByEmail(email);
        return subscriptionRepository.findAllByUserId(user.getId());
    }

    public List<Content> findAllSubscribedContents(String email) {
        List<Subscription> subscriptions = findAllSubscription(email);
        List<Content> contents = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            contents.add(subscription.getContent());
        }
        return contents;
    }

    public List<Content> findAllNonSubscribedContents(String email) {
        List<Content> subscriptions = findAllSubscribedContents(email);
        List<Content> allContents = contentService.findAll();
        List<Content> result = new ArrayList<>();
        for (Content content : allContents) {
            if (!subscriptions.contains(content)) {
                result.add(content);
            }
        }
        return result;
    }

    public boolean checkIfUserSubscribed(int contentId, String email) {
        List<Content> subscriptions = findAllSubscribedContents(email);
        for (Content sub: subscriptions) {
            if (sub.getId() == contentId) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void cancelSubscription(int contentId, String userEmail) {
        List<Subscription> subscriptions = findAllSubscription(userEmail);
        for (Subscription subscription : subscriptions) {
            if (subscription.getContent().getId() == contentId) {
                subscriptionRepository.deleteById(subscription.getId());
                return;
            }
        }
    }

    public Subscription findSubByUserIdAndContentId(UUID uid, int cid) {
        return subscriptionRepository.findByUserIdAndAndContentId(uid, cid);
    }
}
