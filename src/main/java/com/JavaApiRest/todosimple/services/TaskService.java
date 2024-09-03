package com.JavaApiRest.todosimple.services;

import com.JavaApiRest.todosimple.models.Enums.ProfileEnum;
import com.JavaApiRest.todosimple.models.Task;
import com.JavaApiRest.todosimple.models.User;
import com.JavaApiRest.todosimple.repositories.TaskRepository;
import com.JavaApiRest.todosimple.security.UserSpringSecurity;
import com.JavaApiRest.todosimple.services.exceptions.AuthorizationException;
import com.JavaApiRest.todosimple.services.exceptions.DataBindingViolationException;
import com.JavaApiRest.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private DefaultSslBundleRegistry sslBundleRegistry;

    public Task findById(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! id: " + id + "Tipo: " + Task.class.getName()
        ));

        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Faça login para acessar tarefas!");
        else if(!userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task))
            throw new AuthorizationException("Você não tem acesso á esta tarefa!");

        return task;
    }

    public List<Task> findAllByUser() {
        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Faça login para acessar tarefas!");
        return this.taskRepository.findByUser_Id(userSpringSecurity.getId());
    }


    @Transactional
    public Task createTask(Task newTask) {
        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Faça login para criar tarefas!");
        User user = this.userService.findById(userSpringSecurity.getId());
        newTask.setId(null);
        newTask.setUser(user);
        newTask = this.taskRepository.save(newTask);
        return newTask;
    }

    //Busca a task antiga, atualiza e salva
    @Transactional
    public Task updateTask(Task task) {
        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());
        return this.taskRepository.save(newTask);
    }

    public void deleteTask(Long id) {
        Task task = findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException(
                    "Não foi possível deletar a tarefa! "
            );

        }
    }

    //TODO fazer uma função findAll, para buscar todas as tasks do sistema
    // apenas admin podem fazer isso.

    private boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }
}
