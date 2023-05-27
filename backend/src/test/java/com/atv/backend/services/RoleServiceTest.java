package com.atv.backend.services;

import com.atv.backend.dao.entities.Role;
import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.requests.AddRoleRequest;
import com.atv.backend.requests.LoginRequest;
import com.atv.backend.requests.RegisterRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.atv.backend.dao.entities.Role.Action.DISPLAY_USERS;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RoleServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @Test
    public void testCreateUserAndAddRole() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("test");
        registerRequest.setLastName("test");
        registerRequest.setEmail("wer@email.com");
        registerRequest.setPassword("test");
        registerRequest.setPhone("123456789");

        // register user
        User registerUser = userService.registerUser(registerRequest);
        Assert.assertNotNull(registerUser);

        // check if user exists
        boolean userExists = userService.userExists(registerRequest.getEmail());
        Assert.assertTrue(userExists);

        // login user
        Token token = userService.login(new LoginRequest(registerRequest.getEmail(), registerRequest.getPassword(), "test"));
        Assert.assertNotNull(token);


        AddRoleRequest addRoleRequest = new AddRoleRequest(registerUser.getEmail(), DISPLAY_USERS);
        roleService.addRoleToUser(token.getToken(), addRoleRequest);
        List<Role.Action> result = roleService.listActions(token.getToken());
        Assert.assertEquals(1, result.size());

        Assert.assertEquals(DISPLAY_USERS, result.get(0));


    }


}