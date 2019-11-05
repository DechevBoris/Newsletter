package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.Role;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations= "classpath:application-test.properties")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role roleAdmin;
    private Role roleUser;

    @Before
    public void setUp() {
        roleAdmin = Role.builder()
                .role("ADMIN")
                .build();
        roleUser = Role.builder()
                .role("USER")
                .build();
    }

    @AfterEach
    public void clearRepo() {
        roleRepository.deleteAll();
    }

    @Test
    public void saveRoleTest() {

        //check if user's id is still null
        assertEquals(0, roleAdmin.getId());
        assertEquals(0, roleUser.getId());

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        //check if user's id is not null after being persisted
        assertEquals(1, roleAdmin.getId());
        assertEquals(2, roleUser.getId());
    }
}
