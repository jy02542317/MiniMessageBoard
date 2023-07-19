package learning.java.minimessageboard.Controllers;

import jakarta.validation.Valid;
import learning.java.minimessageboard.Dto.LogInDto;
import learning.java.minimessageboard.Dto.SignUpDto;
import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Services.JwtServices;
import learning.java.minimessageboard.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtServices jwtServices;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/findAll")
    public List<TbUserEntity> findAll() {
        return userServices.findAll();
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/LogIn")
    public ResponseEntity<?> Login(@Valid @RequestBody LogInDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtServices.generateToken(loginDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User signed-in failed!.", HttpStatus.OK);
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/SignUp")
    public ResponseEntity<?> createOrSaveUser(@Valid @RequestBody SignUpDto signupDto) {
        return userServices.SaveUser(signupDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam Long id) {
        userServices.DeleteUser(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUsers")
    public void deleteUsers(@RequestParam Long[] ids) {
        userServices.DeleteUsers(Arrays.stream(ids).toList());
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/getUserById")
    public TbUserEntity getUserById(@RequestParam Long id) {
        return userServices.getTbUserById(id).get();
    }
}
