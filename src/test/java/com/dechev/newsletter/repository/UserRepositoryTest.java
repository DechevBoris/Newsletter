package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Role;
import com.dechev.newsletter.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.MockitoAnnotations.initMocks;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations= "classpath:application-test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private User user;

    @Before
    public void setUp() {

        initMocks(this);

        //init role repository
        Mockito.when(roleRepository.findByRole("ADMIN")).thenReturn(Role.builder().role("ADMIN").build());
        Mockito.when(roleRepository.findByRole("USER")).thenReturn(Role.builder().role("USER").build());

        //init user
        user = User.builder()
                .email("user@email.test")
                .password("user_password_test")
                .name("user_name_test")
                .lastName("user_last_name_test")
                .active(1)
                .roles(new HashSet<>(Arrays.asList(roleRepository.findByRole("USER"))))
                .build();
    }

    @Test
    public void saveUserTest() {

        //check if user's id is still null
        assertNull(user.getId());

        userRepository.save(user);

        //check if user's id is not null after being persisted
        assertNotNull(user.getId());
    }

    @Test
    public void findByEmailTest() {

        userRepository.save(user);

        User found = userRepository.findByEmail(user.getEmail());

        //check if all the properties of the user match with the ones of the found
        assertEquals(user.getEmail(), found.getEmail());
        assertEquals(user.getPassword(), found.getPassword());
        assertEquals(user.getLastName(), found.getLastName());
        assertEquals(user.getName(), found.getName());
        assertEquals("USER", found.getRoles().stream().findFirst().get().getRole());
    }

    @Test
    public void findAllTest() {

        userRepository.save(user);

        List<User> result = userRepository.findAll();

        assertEquals(1, result.size());
    }
}
