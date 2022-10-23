package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.businesslayer.*;

import javax.validation.Valid;

@RestController
@Validated
public class RegistrationController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity register(@RequestBody @Valid User user) {
        try {
            userService.loadUserByUsername(user.getEmail());
        } catch (UsernameNotFoundException ex) {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
