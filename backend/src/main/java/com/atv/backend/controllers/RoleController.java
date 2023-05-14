package com.atv.backend.controllers;

import com.atv.backend.dao.entities.Role;
import com.atv.backend.requests.AddRoleRequest;
import com.atv.backend.services.RoleService;
import com.atv.backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping("/add-role")
    public String addRole(@RequestBody AddRoleRequest addRoleRequest,@RequestHeader("Authorization") String token){
        roleService.addRoleToUser(token, addRoleRequest);
        return "succes";

    }

    @GetMapping("/my-roles")
    public List<Role.Action> listRoles(@RequestHeader("Authorization") String token){
        return roleService.listActions(token);
    }
}
