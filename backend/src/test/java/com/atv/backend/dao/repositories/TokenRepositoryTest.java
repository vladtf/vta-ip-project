package com.atv.backend.dao.repositories;

import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createTokensForUser() {
        User user1 = new User("Alice", "lastname", "password", "test23@email.com", "123456789", "123 Main St");
        userRepository.save(user1);


        // Create 2 new tokens for different devices
        Token token = new Token("test", user1);
        Token token2 = new Token("tes2", user1);

        // Save tokens
        tokenRepository.save(token);
        tokenRepository.save(token2);


        // Get all tokens for user
        List<Token> tokens = userRepository.findByEmail(user1.getEmail()).getTokens();

        assertEquals(2, tokens.size());

        assertEquals(token.getToken(), tokens.get(0).getToken());
        assertEquals(token2.getToken(), tokens.get(1).getToken());
    }

    @Test
    public void testTokeExpired() {
        User user1 = new User("Bob", "lastname", "password", "test31@email.com", "123456789", "123 Main St");
        userRepository.save(user1);

        Token token = new Token("test", user1);

        // Set token to expire yesterday
        token.setExpiresAt(Date.valueOf(LocalDate.now().minusDays(1)));

        // Save token
        tokenRepository.save(token);


        List<Token> tokens = userRepository.findByEmail(user1.getEmail()).getTokens();
        assertEquals(1, tokens.size());

        // Check if token is expired
        assertTrue(tokenRepository.isTokenExpired(token.getToken()));

    }

}