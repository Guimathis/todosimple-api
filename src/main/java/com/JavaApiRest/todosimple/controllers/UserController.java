package com.JavaApiRest.todosimple.controllers;

import com.JavaApiRest.todosimple.models.User;
import com.JavaApiRest.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.JavaApiRest.todosimple.models.User.CreateUser;
import com.JavaApiRest.todosimple.models.User.UpdateUser;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findByID(@PathVariable Long id){
        User objUser = this.userService.findById(id);
        return ResponseEntity.ok().body(objUser);
    }

    @PostMapping
    @Validated(CreateUser.class)
    public ResponseEntity<Void> createUser(@Valid @RequestBody User obj){
        this.userService.createUser(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.updateUser(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
