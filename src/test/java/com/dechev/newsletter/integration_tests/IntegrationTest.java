//package com.dechev.newsletter.integration_tests;
//
//import com.dechev.newsletter.model.Content;
//import com.dechev.newsletter.model.Role;
//import com.dechev.newsletter.model.User;
//import com.dechev.newsletter.repository.ContentRepository;
//import com.dechev.newsletter.repository.RoleRepository;
//import com.dechev.newsletter.repository.SubscriptionRepository;
//import com.dechev.newsletter.repository.UserRepository;
//import com.dechev.newsletter.service.ContentService;
//import com.dechev.newsletter.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
////@ActiveProfiles("integration-test")
//@RunWith(SpringRunner.class)
////@TestPropertySource(locations = "classpath:application-test.properties")
//@TestPropertySource(locations = "classpath:application.properties")
//@SpringBootTest
//public class IntegrationTest {
//
//    @Autowired
//    private static UserRepository userRepository;
//    @Autowired
//    private static RoleRepository roleRepository;
//    @Autowired
//    private static SubscriptionRepository subscriptionRepository;
//    @Autowired
//    private static ContentRepository contentRepository;
//    @Autowired
//    private static BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private UserService userService;
//
//    @TestConfiguration
//    static class IntegrationTestContextConfiguration {
//        @Bean
//        public UserService userService() {
//            return new UserService(userRepository, roleRepository, subscriptionRepository, bCryptPasswordEncoder);
//        }
//
////        @Bean
////        public ContentService contentService() {
////            return new ContentService(contentRepository, userRepository);
////        }
////
////        @Bean
////        public BCryptPasswordEncoder bCryptPasswordEncoder() {
////            return new BCryptPasswordEncoder();
////        }
//    }
//
//    @Test
//    public void subscribeAndUnsubscribeTest() {
//        if (roleRepository != null) {
//            roleRepository.save(Role.builder().role("USER").build());
//        }
//        User user = User.builder()
//                .email("user@email.test")
//                .password("user_password_test")
//                .name("user_name_test")
//                .lastName("user_lastname_test")
//                .active(1)
//                .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("USER"))))
//                .build();
//        Content content1 = Content.builder()
//                .title("content1_title_test")
//                .authorEmail("content1@authoremail.test")
//                .description("content1_description")
//                .text("content1_text")
//                .build();
//        Content content2 = Content.builder()
//                .title("content2_title_test")
//                .authorEmail("content2@authoremail.test")
//                .description("content2_description")
//                .text("content2_text")
//                .build();;
//        Content content3 = Content.builder()
//                .title("content3_title_test")
//                .authorEmail("content3@authoremail.test")
//                .description("content3_description")
//                .text("content3_text")
//                .build();
//
//        userService.subscribe(user.getEmail(), content1.getId());
//        userService.subscribe(user.getEmail(), content2.getId());
//        userService.subscribe(user.getEmail(), content3.getId());
//
//        List<Content> resultContents = userService.findAllSubscribedContents(user.getEmail());
//
//        assertEquals(content1, resultContents.get(0));
//        assertEquals(content2, resultContents.get(1));
//        assertEquals(content3, resultContents.get(2));
//    }
//}
