package com.JavaApiRest.todosimple.controllers;

import com.JavaApiRest.todosimple.models.Task;
import com.JavaApiRest.todosimple.models.projections.TaskProjection;
import com.JavaApiRest.todosimple.services.TaskService;
import com.JavaApiRest.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findbyId(@PathVariable Long id){
        Task objTask = this.taskService.findById(id);
        return ResponseEntity.ok().body(objTask);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskProjection>> findAllByUser(){
        List<TaskProjection> obj = this.taskService.findAllByUser();
        return ResponseEntity.ok().body(obj);

    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> createTask(@Valid @RequestBody Task obj){
        this.taskService.createTask(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> uptadeTask(@Valid @RequestBody Task obj, @PathVariable Long id ){
        obj.setId(id);
        this.taskService.updateTask(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id){
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
