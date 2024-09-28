package com.JavaApiRest.todosimple.services;

import com.JavaApiRest.todosimple.models.Enums.ProfileEnum;
import com.JavaApiRest.todosimple.models.User;
import com.JavaApiRest.todosimple.models.dto.UserCreateDTO;
import com.JavaApiRest.todosimple.models.dto.UserUpdateDTO;
import com.JavaApiRest.todosimple.repositories.UserRepository;
import com.JavaApiRest.todosimple.security.UserSpringSecurity;
import com.JavaApiRest.todosimple.services.exceptions.AuthorizationException;
import com.JavaApiRest.todosimple.services.exceptions.DataBindingViolationException;
import com.JavaApiRest.todosimple.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    // Testa se o usuário está logado e seu token está valido
//    e se tem permissão de admin e está procurando pelo id dele
    public User findById(Long id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
                && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    public User findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(this.userRepository.findByUsername(username));
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Username: " + username + ", Tipo: "));
    }

    @Transactional //Usar anotação para operações de criação/exclusão no BD
    public User createUser(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;

    }

    //Busca o usuário antigo, atualiza e salva
    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não foi possivel deletar o Usuário " + user.getUsername() + ", pois há tarefas ligadas a ele.");
        }
    }

    public UserSpringSecurity authenticated() {
        try {
            User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            return new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
        } catch (Exception e) {
            return null;
        }
    }

    public User fromDTO(@Valid UserCreateDTO obj){
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO obj) {
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user;
    }
}
