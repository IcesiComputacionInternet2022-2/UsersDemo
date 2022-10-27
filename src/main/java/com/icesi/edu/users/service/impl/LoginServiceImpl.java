package com.icesi.edu.users.service.impl;

import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.model.User;
import com.icesi.edu.users.repository.UserRepository;
import com.icesi.edu.users.service.LoginService;
import com.icesi.edu.users.utils.JWTParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.stream.StreamSupport;

import static com.icesi.edu.users.constant.UserErrorCode.CODE_02;

@AllArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    public final UserRepository userRepository;

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        User user = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .peek(u -> System.out.println(u.getEmail()))
                .filter(u -> u.getEmail().equalsIgnoreCase(loginDTO.getEmail()) )
                .filter(u -> u.getPassword().equalsIgnoreCase(loginDTO.getPassword()))
                .findFirst()
                .orElse(null);
        if(user != null) {
            HashMap<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            return new TokenDTO(JWTParser.createJWT(user.getId().toString(), user.getFirstName(), user.getFirstName(), claims, 3600000L));
        }
        throw new UserException(HttpStatus.BAD_REQUEST, new UserError(CODE_02, CODE_02.getMessage()));
    }

}
