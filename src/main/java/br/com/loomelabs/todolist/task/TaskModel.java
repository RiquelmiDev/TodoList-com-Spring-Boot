package br.com.loomelabs.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks") //nome da tabela no banco de dados
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID") //Gera um valor único automaticamente
    private UUID id;
    private String description;
    
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;  
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw new Exception("O título não pode ter mais de 50 caracteres.");
        }
        this.title = title;
    }

}
