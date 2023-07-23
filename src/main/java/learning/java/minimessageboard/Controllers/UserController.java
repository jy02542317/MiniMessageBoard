package learning.java.minimessageboard.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Tag(name = "1. 用户模块", description = "定义用户接口")
@RestController
@RequestMapping("/api/User")
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtServices jwtServices;
    @Operation(summary = "查找所有用户", security = @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/findAll")
    public List<TbUserEntity> findAll() {
        return userServices.findAll();
    }
    @Operation(summary = "登陆接口", description = "输入用户信息进行登陆")
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
    @Operation(summary = "注册接口", description = "输入用户信息进行注册")
    @PreAuthorize("permitAll()")
    @PostMapping("/SignUp")
    public ResponseEntity<?> createOrSaveUser(@Valid @RequestBody SignUpDto signupDto) {
        return userServices.SaveUser(signupDto);
    }

    @Operation(summary = "注册接口", description = "通过inviteCode进行登陆")
    @PreAuthorize("permitAll()")
    @PostMapping("/SignUp/{inviteCode}")
    public ResponseEntity<?> createOrSaveUser(@PathVariable String inviteCode) {
        return userServices.AutoSignByInviteCode(inviteCode);
    }
    @Operation(summary = "通过ID删除用户", security = @SecurityRequirement(name = "Authorization"))
    @Parameters({@Parameter(name="id",description = "用户ID",required = true)})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam Long id) {
        userServices.DeleteUser(id);
    }

    @Operation(summary = "通过ID批量删除用户", security = @SecurityRequirement(name = "Authorization"))
    @Parameters({@Parameter(name="ids",description = "用户ID",required = true)})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUsers")
    public void deleteUsers(@RequestParam Long[] ids) {
        userServices.DeleteUsers(Arrays.stream(ids).toList());
    }

    @Operation(summary = "通过ID查找用户", security = @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Parameters({@Parameter(name="id",description = "用户ID",required = true)})
    @PostMapping("/getUserById")
    public TbUserEntity getUserById(@RequestParam Long id)  {
        return userServices.getTbUserById(id).orElse(new TbUserEntity());
    }
}
