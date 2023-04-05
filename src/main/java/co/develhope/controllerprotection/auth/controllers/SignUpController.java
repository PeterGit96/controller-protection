package co.develhope.controllerprotection.auth.controllers;

import co.develhope.controllerprotection.auth.dto.SignUpActivationDTO;
import co.develhope.controllerprotection.auth.dto.SignUpDTO;
import co.develhope.controllerprotection.auth.services.SignUpService;
import co.develhope.controllerprotection.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public UserDTO signup(@RequestBody SignUpDTO signUpDTO) {
        return signUpService.signUp(signUpDTO);
    }

    @PostMapping("/signup/activation")
    public UserDTO signup(@RequestBody SignUpActivationDTO signUpActivationDTO) {
        return signUpService.activate(signUpActivationDTO);
    }

}
