package com.JavaApiRest.todosimple.services;

import com.JavaApiRest.todosimple.models.User;
import com.JavaApiRest.todosimple.repositories.TaskRepository;
import com.JavaApiRest.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado. Id:" + id + ", Tipo: " + User.class.getName()
        ));
    }
    @Transactional //Usar anotação para operações de criação/exclusão no BD
    public User createUser(User newUser){
        newUser.setId(null);
        newUser = this.userRepository.save(newUser);
        return newUser;

    }
    //Busca o usuário antigo, atualiza e salva
    @Transactional
    public User userUpdate(User user) {
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        return this.userRepository.save(newUser);
    }

    public void deleteUser(Long id){
        User user = findById(id);
        try {
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não foi possivel deletar o Usuário " + user.getUsername() + ", pois há tarefas ligadas a ele." );
        }
    }

}
