package br.edu.ifpb.dac.sistemadehorarios.entity.User;

import br.edu.ifpb.dac.sistemadehorarios.DTO.TokenDTO;
import br.edu.ifpb.dac.sistemadehorarios.DTO.UserDTO;
import br.edu.ifpb.dac.sistemadehorarios.entity.User.utils.LoginDRO;
import br.edu.ifpb.dac.sistemadehorarios.exception.UserInvalidException;
import br.edu.ifpb.dac.sistemadehorarios.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService service;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDRO loginDRO){
        UsernamePasswordAuthenticationToken dadosLogin = loginDRO.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            UserModel user = (UserModel) authentication.getPrincipal();
            String token = tokenService.generateTokenJwt(authentication);
            UserDTO userDTO = UserDTO.builder()
                    .uuid(user.getUuid())
                    .email(user.getEmail())
                    .name(user.getName())
                    .roles(user.getAuthorities())
                    .token(token)
                    .build();
            userDTO.setToken(token);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getStackTrace());
        }
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity create(@RequestBody UserModel user) throws UserInvalidException {

        UserModel userModel =  this.service.create(user);
        if(userModel != null){
            return ResponseEntity.status(201).body(userModel);
        }
        return ResponseEntity.status(400).body("Bad request");

    }
    @PostMapping("/refreshToken")
    public ResponseEntity isTokenValid(@RequestBody TokenDTO tokenDTO) throws UserInvalidException {

        String token = tokenService.refresh(tokenDTO.getToken(), tokenDTO.getUserUuid());
        return ResponseEntity.status(200).body(token);

    }
    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<String> delete(@PathVariable("uuid") String uuid){

        boolean result = this.service.delete(uuid);
        if (result) {
            return ResponseEntity.status(200).body("OK");
        }
        return ResponseEntity.status(404).body("NOT OK");

    }
}
