package com.atv.backend.dao.repositories;

import com.atv.backend.dao.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User generateUser() {
        User user = new User("Alice", "lastname", "password", "test11@email.com", "123456789", "123 Main St");
        user.setEmail(UUID.randomUUID().toString());
        return user;
    }


    @Test
    public void testFetchData() {
        deleteDataForCleanup();

        User userA = generateUser();
        userRepository.save(userA);

        /*Test data retrieval*/
        User foundUser = userRepository.findByEmail(userA.getEmail());
        assertNotNull(foundUser);
        assertEquals(userA.getEmail(), foundUser.getEmail());
        /*Get all products, list should only have two*/
        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
    }

    @Test
    public void testSaveData() {
        deleteDataForCleanup();

        // Create 3 new users
        for (int i = 0; i < 3; i++) {
            User user = new User("User" + i, "lastname", "password", "test@email.com", "123456789", "123 Main St");
            userRepository.save(user);
        }

        // Get all users, list should only have 3
        List<User> users = userRepository.findAll();
        assertEquals(3, users.size());
    }

    private void deleteDataForCleanup() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            userRepository.delete(user);
        }
    }
}