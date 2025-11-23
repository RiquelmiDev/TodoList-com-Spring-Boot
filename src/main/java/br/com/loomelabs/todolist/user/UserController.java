package br.com.loomelabs.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired // gerencia o ciclo de vida do objeto
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody UserModel userModel){
        // @RequestBody pega o corpo da requisição e converte para o objeto UserModel

        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe!");
        }
        // Faz um hash da senha antes de salvar no banco de dados
        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        // Seta a senha com o hash
        userModel.setPassword(passwordHashred);

        // Salva o usuario no banco de dados
        var userCreated = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    
}