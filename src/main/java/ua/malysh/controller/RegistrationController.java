package ua.malysh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.malysh.dto.UserRegistrationForm;
import ua.malysh.service.UserService;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Long> userRegister(@RequestBody UserRegistrationForm userRegistrationForm) {
        return new ResponseEntity<>(userService.saveUser(userRegistrationForm), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<Long> adminRegistration(@RequestBody UserRegistrationForm userRegistrationForm) {
        return new ResponseEntity<>(userService.saveAdminUser(userRegistrationForm), HttpStatus.CREATED);
    }
}
