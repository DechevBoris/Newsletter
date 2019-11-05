package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Content;
import com.dechev.newsletter.model.Role;
import com.dechev.newsletter.model.Subscription;
import com.dechev.newsletter.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations= "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private RoleRepository roleRepository;

    private Subscription subscription;
    private Subscription subscription2;
    private User user;
    private User user2;
    private Content content;

    @Before
    public void setUp() {

        subscriptionRepository.deleteAll();
        subscriptionRepository.flush();

        initMocks(this);

        Mockito.when(roleRepository.findByRole("USER")).thenReturn(Role.builder().role("USER").build());

        user = User.builder()
                .email("user@test.email")
                .password("user_test_password")
                .lastName("user_test_lastname")
                .name("user_test_name")
                .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("USER"))))
                .active(1)
                .build();

        user2 = User.builder()
                .email("user2@test.email")
                .password("user2_test_password")
                .lastName("user2_test_lastname")
                .name("user2_test_name")
                .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("USER"))))
                .active(1)
                .build();

        content = Content.builder()
                .title("content_title")
                .authorEmail("content_author")
                .description("content_description")
                .text("content_text")
                .build();

        subscription = Subscription.builder()
                .user(user)
                .content(content)
                .dateOfSubscription(LocalDateTime.now())
                .build();

        subscription2 = Subscription.builder()
                .user(user2)
                .content(content)
                .dateOfSubscription(LocalDateTime.now())
                .build();
    }

    @Test
    public void saveSubscriptionTest() {

        //check if user's id is still null
        assertEquals(0, subscription.getId());

        subscriptionRepository.save(subscription);

        //check if user's id is not null after being persisted
        assertEquals(1, subscription.getId());
    }

    @Test
    public void findByUserIdAndAndContentIdTest() {

        // persist a subscription
        subscriptionRepository.save(subscription);
        Subscription result = subscriptionRepository.findByUserIdAndAndContentId(user.getId(), content.getId());

        // check if the user and content of the result have the same id's as the ones, given as parameters
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(content.getId(), result.getContent().getId());
    }

    @Test
    public void findAllByUserIdTest() {

        // persist two objects
        subscriptionRepository.save(subscription);
        subscriptionRepository.save(subscription2);

        // check if the id of the results' user matches the real user id
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(user.getId());
        assertEquals(user.getId(), subscriptions.get(0).getUser().getId());

        List<Subscription> subscriptions2 = subscriptionRepository.findAllByUserId(user.getId());
        assertEquals(user.getId(), subscriptions2.get(0).getUser().getId());

    }
}
