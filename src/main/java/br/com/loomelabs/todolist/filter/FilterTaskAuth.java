package br.com.loomelabs.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.loomelabs.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Anotacao para registrar o filtro como um componente do Spring, como se
           // falasse para o spring "Ei, gerencie essa classe ai pra mim antes de ir pro
           // controller"
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {
            // Lógica de autenticação ou autorização

            // Pegar a autenticação do usuário (usuario e senha)
            var authorization = request.getHeader("Authorization");
            System.out.println("Authorization: " + authorization);

            // Remover o "Basic " do começo da string
            var authEncoded = authorization.substring("Basic".length()).trim();

            // Decodificar a string Base64
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            // Converter os bytes decodificados para string
            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validar usuario
            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
            } else {

                // Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (passwordVerify.verified) {
                    // Adicione o ID do usuário como atributo na requisição
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response); // Continua para o proximo filtro ou controller
                } else {
                    response.sendError(401);
                }

                // Segue viagem
            }
        }else{
            filterChain.doFilter(request, response);
        }

    }
}
