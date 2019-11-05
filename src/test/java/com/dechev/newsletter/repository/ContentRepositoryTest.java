package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Content;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations= "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContentRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;

    private Content content1;
    private Content content2;
    private Content content3;

    @Before
    public void setUp() {

        contentRepository.deleteAll();
        contentRepository.flush();

        content1 = Content.builder()
                .title("content1_title")
                .authorEmail("content1_authorEmail")
                .description("content1_description")
                .text("content1_text")
                .build();
        content2 = Content.builder()
                .title("content2_title")
                .authorEmail("content2_authorEmail")
                .description("content2_description")
                .text("content2_text")
                .build();
        content3 = Content.builder()
                .title("content3_title")
                .authorEmail("content3_authorEmail")
                .description("content3_description")
                .text("content3_text")
                .build();
    }

    @Test
    public void saveContentTest() {

        //check if user's id is still null
        assertEquals(0, content1.getId());
        assertEquals(0, content2.getId());
        assertEquals(0, content3.getId());

        contentRepository.save(content1);
        contentRepository.save(content2);
        contentRepository.save(content3);


        //check if user's id is not null after being persisted
        assertEquals(1, content1.getId());
        assertEquals(2, content2.getId());
        assertEquals(3, content3.getId());
    }

    @Test
    public void findContentByTitleTest() {

        // persist three objects
        contentRepository.save(content1);
        contentRepository.save(content2);
        contentRepository.save(content3);

        Content result1 = contentRepository.findContentByTitle(content1.getTitle());
        Content result2 = contentRepository.findContentByTitle(content2.getTitle());
        Content result3 = contentRepository.findContentByTitle(content3.getTitle());

        // check if the titles of the real and the retrieved contents match
        assertEquals(content1.getTitle(), result1.getTitle());
        assertEquals(content1.getAuthorEmail(), result1.getAuthorEmail());
        assertEquals(content2.getTitle(), result2.getTitle());
        assertEquals(content2.getAuthorEmail(), result2.getAuthorEmail());
        assertEquals(content3.getTitle(), result3.getTitle());
        assertEquals(content3.getAuthorEmail(), result3.getAuthorEmail());
    }
}
