package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contentRepository")
public interface ContentRepository extends JpaRepository<Content, Long> {

    Content findContentByTitle(String title);

    List<Content> findAll();

    Content findById(int id);
}
