package com.dechev.newsletter.service;

import com.dechev.newsletter.dto.UserDto;
import com.dechev.newsletter.model.Role;
import com.dechev.newsletter.model.User;
import com.dechev.newsletter.repository.RoleRepository;
import com.dechev.newsletter.repository.SubscriptionRepository;
import com.dechev.newsletter.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

//    @Mock
//    private UserRepository mockUserRepository;
//
//    @Mock
//    private RoleRepository mockRoleRepository;
//
//    @Mock
//    private BCryptPasswordEncoder mockBCryptPasswordEncoder;
//
//    @Mock
//    private SubscriptionRepository mockSubscriptionRepository;
//
//    private UserService userServiceUnderTest;
//
//    private User user;
//
//
//    @Before
//    public void setUp() {
//        initMocks(this);
//        userServiceUnderTest = new UserService(mockUserRepository, mockRoleRepository, mockSubscriptionRepository, mockBCryptPasswordEncoder);
//
//        user = User.builder()
//                .id(UUID.randomUUID())
//                .name("Boris")
//                .lastName("Dechev")
//                .email("test@test.com")
//                .password("12345678")
//                .roles(new HashSet<>(Arrays.asList(mockRoleRepository.findByRole("USER"))))
//                .active(1)
//                .build();
//
//        Mockito.when(mockUserRepository.save(any())).thenReturn(user);
//        Mockito.when(mockUserRepository.findByEmail(anyString())).thenReturn(user);
//    }
//
//    @Test
//    public void testFindUserByEmail() {
//        // Setup
//        final String email = "test@test.com";
//
//        // Run the test
//        final User result = userServiceUnderTest.findUserByEmail(email);
//
//        // Verify the results
//        assertEquals(email, result.getEmail());
//    }
//
//    @Test
//    public void testSaveUser() {
//
////FIXME anton : check is the bean initializeds
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        UserDto testData  = new UserDto();
//        testData.setPassword("alabala");
//        testData.setEmail("test@test.com");
//        // Run the tests
//        Mockito.when(mockRoleRepository.findByRole(any())).thenReturn(new Role());
//        User result = userServiceUnderTest.saveUser(UserDto.builder().build());
//        Assert.notNull(user, "Problem during saving");
//        assertEquals(bCryptPasswordEncoder.encode(testData.getPassword()), result.getPassword());
//        // Verify the results
//        assertEquals(user.getEmail(),result.getEmail());
//    }
}
