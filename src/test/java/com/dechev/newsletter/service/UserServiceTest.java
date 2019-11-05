package com.dechev.newsletter.service;

import com.dechev.newsletter.dto.UserDto;
import com.dechev.newsletter.model.Role;
import com.dechev.newsletter.model.User;
import com.dechev.newsletter.repository.ContentRepository;
import com.dechev.newsletter.repository.RoleRepository;
import com.dechev.newsletter.repository.SubscriptionRepository;
import com.dechev.newsletter.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @Mock
    private static UserRepository mockUserRepository;

    @Mock
    private static RoleRepository mockRoleRepository;

    @Mock
    private static SubscriptionRepository mockSubscriptionRepository;

    @Mock
    private static ContentRepository mockContentRepository;

    @Mock
    private static BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private User user;

    @Autowired
    @InjectMocks
    private UserService userServiceUnderTest;

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserService(mockUserRepository, mockRoleRepository, mockSubscriptionRepository, mockBCryptPasswordEncoder);
        }

        @Bean
        public ContentService contentService() {
            return new ContentService(mockContentRepository, mockUserRepository);
        }

    }

    @Before
    public void setUp() {
        initMocks(this);

        Mockito.when(mockRoleRepository.findByRole("USER")).thenReturn(Role.builder().id(2).role("USER").build());

        user = User.builder()
                .id(UUID.randomUUID())
                .name("user_name_test")
                .lastName("user_lastname_test")
                .email("user@email.test")
                .password("user_password_test")
                .roles(new HashSet<>(Arrays.asList(mockRoleRepository.findByRole("USER"))))
                .active(1)
                .build();

        Mockito.when(mockUserRepository.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(mockBCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encrypted_user_password_test");
    }

    @Test
    public void saveUserTest() {
        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .confirmPassword(user.getPassword())
                .name(user.getName())
                .lastName(user.getLastName())
                .build();

        User result = userServiceUnderTest.saveUser(userDto);

        assertNotNull(result);
        assertEquals("encrypted_user_password_test", result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRoles(), result.getRoles());
    }
}
