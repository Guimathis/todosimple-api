package com.JavaApiRest.todosimple.services;

import com.JavaApiRest.todosimple.models.Enums.ProfileEnum;
import com.JavaApiRest.todosimple.models.User;
import com.JavaApiRest.todosimple.repositories.TaskRepository;
import com.JavaApiRest.todosimple.repositories.UserRepository;
import com.JavaApiRest.todosimple.services.exceptions.DataBindingViolationException;
import com.JavaApiRest.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;



    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException (
                "Usuário não encontrado. Id:" + id + ", Tipo: " + User.class.getName()
        ));
    }
    @Transactional //Usar anotação para operações de criação/exclusão no BD
    public User createUser(User obj){
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;

    }
    //Busca o usuário antigo, atualiza e salva
    @Transactional
    public User updateUser(User user) {
        User newUser = findById(user.getId());
        newUser.setPassword(this.bCryptPasswordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }

    public void deleteUser(Long id){
        User user = findById(id);
        try {
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new DataBindingViolationException("Não foi possivel deletar o Usuário " + user.getUsername() + ", pois há tarefas ligadas a ele." );
        }
    }

}
