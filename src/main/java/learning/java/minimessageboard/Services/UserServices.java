package learning.java.minimessageboard.Services;

import learning.java.minimessageboard.Dto.LogInDto;
import learning.java.minimessageboard.Dto.SignUpDto;
import learning.java.minimessageboard.Entities.TbRoleEntity;
import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Repository.RoleRepository;
import learning.java.minimessageboard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServices implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public Optional<TbUserEntity> getTbUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<TbUserEntity> findAll() {
        return userRepository.findAll();
    }


    public ResponseEntity<?> SaveUser(SignUpDto signupDto) {
        // add check for username exists in a DB
        if(userRepository.existsByUserName(signupDto.getName())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        TbUserEntity userEntity = new TbUserEntity();
        userEntity.setUserName(signupDto.getName());
        userEntity.setPassWord(passwordEncoder(signupDto.getPassword()));
        if(signupDto.getIsAdmin())
        {
            userEntity.setRoleList(roleRepository.findByRoleName("ROLE_ADMIN").stream().toList());
        }
        else{
            userEntity.setRoleList(roleRepository.findByRoleName("ROLE_USER").stream().toList());
        }
        userRepository.save(userEntity);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    public void DeleteUsers(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Boolean isValid=true;
        TbUserEntity user = userRepository.findByUserNameOrEmailAndIsValid(usernameOrEmail, usernameOrEmail,isValid)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        Set<GrantedAuthority> authorities = user
                .getRoleList()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassWord(),
                authorities);
    }


    private String passwordEncoder(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }
}
