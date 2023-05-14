package com.atv.backend.services;

import com.atv.backend.dao.entities.Role;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.RolesRepository;
import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import com.atv.backend.requests.AddRoleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final RolesRepository rolesRepository;

    private final UserService userService;


    public RoleService(UserRepository userRepository, TokenRepository tokenRepository, RolesRepository rolesRepository, UserService userService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.rolesRepository = rolesRepository;
        this.userService = userService;
    }


    public void addRoleToUser(String token, AddRoleRequest addRoleRequest) {
        String email = addRoleRequest.getEmail();

        if(!userRepository.existsByEmail(email)){
            throw new RuntimeException("Email doesn't exist");
        }
        User user = userRepository.findByEmail(email);
        List<Role> roles=user.getRoles();
        for (Role role : roles) {
            if(role.getAction().equals(addRoleRequest.getAction()))
                throw new RuntimeException("Role already added.");
        }

        Role role=new Role();
        role.setAction(addRoleRequest.getAction());
        role.setUser(user);

        rolesRepository.save(role);
    }


    public List<Role.Action> listActions(String token) {
        User user= userService.getUserByToken(token);
        return user.getRoles().stream().map(Role::getAction).toList();
    }

}
