package com.dechev.newsletter.service;

import com.dechev.newsletter.model.Content;
import com.dechev.newsletter.model.User;
import com.dechev.newsletter.repository.ContentRepository;
import com.dechev.newsletter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("contentService")
public class ContentService {

    private ContentRepository contentRepository;

    private UserRepository userRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository, UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    public Content findContentByTitle(String title) {
        return contentRepository.findContentByTitle(title);
    }

    public boolean addContent(Content content, String authorEmail) {
        content.setAuthorEmail(authorEmail);
        contentRepository.save(content);
        return true;
    }

    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    public Content findById(int id) {
        return contentRepository.findById(id);
    }
}
