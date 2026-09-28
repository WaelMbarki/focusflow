package com.focusflow.demo.controller;

import com.focusflow.demo.dto.UserDTO;
import com.focusflow.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUser()
    {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }
    @PostMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestParam String fieldName, String field)
    {
        return ResponseEntity.ok(userService.updateUser(fieldName,field));
    }
}
