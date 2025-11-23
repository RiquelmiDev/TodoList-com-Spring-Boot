package br.com.loomelabs.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<UserModel, UUID> { //primeiro parametro é o tipo da entidade ou seja qual a classe que ele esta implementando, segundo é o tipo do id que esta sendo usado no model.
    UserModel findByUsername(String username);
    
}
