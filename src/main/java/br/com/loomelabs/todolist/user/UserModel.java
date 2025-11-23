package br.com.loomelabs.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //Adiciona getters e setters automaticamente
@Entity(name = "tb_users") //Define o nome da tabela no banco de dados
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID") //Gera um valor único automaticamente
    private UUID id;

    //Define automaticamente os campos da tabela no db

    @Column(nullable = false, unique = true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
