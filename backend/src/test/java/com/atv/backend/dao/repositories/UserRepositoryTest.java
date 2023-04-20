package com.atv.backend.dao.repositories;

import com.atv.backend.dao.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private void setUp() {
        User user1 = new User("Alice", "password", "test@email.com", "123456789", "123 Main St");
        User user2 = new User("Bob", "password", "test2@email.com", "123456789", "123 Main St");
        //save user, verify has ID value after save
        assertNull(user1.getId());
        assertNull(user2.getId());//null before save
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    }

    @Test
    public void testFetchData() {
        deleteDataForCleanup();
        setUp();

        /*Test data retrieval*/
        User userA = userRepository.findByName("Bob");
        assertNotNull(userA);
        assertEquals("Bob", userA.getName());
        /*Get all products, list should only have two*/
        Iterable<User> users = userRepository.findAll();
        int count = 0;
        for (User p : users) {
            count++;
        }

        assertEquals(count, 2);
    }

    @Test
    public void testSaveData() {
        deleteDataForCleanup();

        // Create 3 new users
        for (int i = 0; i < 3; i++) {
            User user = new User("User" + i, "password", "test@email.com", "123456789", "123 Main St");
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