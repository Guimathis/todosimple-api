package com.JavaApiRest.todosimple.services;

import com.JavaApiRest.todosimple.models.Task;
import com.JavaApiRest.todosimple.models.User;
import com.JavaApiRest.todosimple.repositories.TaskRepository;
import com.JavaApiRest.todosimple.services.exceptions.DataBindingViolationException;
import com.JavaApiRest.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private DefaultSslBundleRegistry sslBundleRegistry;

    //todo Este método não leva em consideração se uma task está associada à um usuário
    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! id: " + id + "Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId (Long userId){
        return this.taskRepository.findByUser_Id(userId);
    }


    @Transactional
    public Task createTask(Task newTask){
        User user = this.userService.findById(newTask.getUser().getId());
        newTask.setId(null);
        newTask.setUser(user);
        newTask = this.taskRepository.save(newTask);
        return newTask;
    }

    //Busca a task antiga, atualiza e salva
    @Transactional
    public Task updateTask(Task task){
        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());
        return this.taskRepository.save(newTask);
    }

    public void deleteTask(Long id){
        Task task = findById(id);
        // Try catch não obrigatório, task não é chave estrangeira
        // try está aqui para se no futuro isso venha a ser implementado
        try {
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw new DataBindingViolationException(
                    "Não foi possível deletar a tarefa! "
            );

        }
    }
}
