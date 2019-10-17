package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Role;
import com.dechev.newsletter.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations= "classpath:application-test.properties")
public class UserRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleRepository.save(new Role(1, "ADMIN"));
        roleRepository.save(new Role(2, "USER"));
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {

        User user = new User();
        user.setEmail("test@test.test");
        user.setLastName("lastNameTest");
        user.setName("nameTest");
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("USER"))));
        user.setActive(1);
        user.setPassword("12345678");

        //check if user's id is still null
        assertNull(user.getId());

        userRepository.save(user);

        //check if user's id is not null after being persisted
        assertNotNull(user.getId());

        User found = userRepository.findByEmail(user.getEmail());
        assertEquals(found.getEmail(), user.getEmail());

        
    }
}
