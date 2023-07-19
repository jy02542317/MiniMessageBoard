package learning.java.minimessageboard.Services;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import learning.java.minimessageboard.Common.MMBConstants;
import learning.java.minimessageboard.Dto.LogInDto;
import learning.java.minimessageboard.Dto.SignUpDto;
import learning.java.minimessageboard.Entities.TbRoleEntity;
import learning.java.minimessageboard.Entities.TbRoomEntity;
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

import java.util.*;
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
        createUser(signupDto.getName(),signupDto.getPassword(),signupDto.getIsAdmin(),false,null);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    private TbUserEntity createUser(String username, String password, boolean isAdmin, boolean isTemp, @Nullable TbRoomEntity tbRoomEntity){
        TbUserEntity userEntity = new TbUserEntity();
        userEntity.setUserName(username);
        userEntity.setPassWord(passwordEncoder(password));
        if(isAdmin)
            userEntity.setRoleList(roleRepository.findByRoleName("ROLE_ADMIN").stream().toList());
        else
            userEntity.setRoleList(roleRepository.findByRoleName("ROLE_USER").stream().toList());
        if(isTemp){
            userEntity.setValid(false);
            UUID uuid = UUID.randomUUID();
            userEntity.setInviteCode(uuid.toString());
            List<TbRoomEntity> list= new ArrayList<>();
            list.add(tbRoomEntity);
            userEntity.setRoomList(list);
        }
       return userRepository.save(userEntity);
    }

    public TbUserEntity saveUser(TbUserEntity tbUserEntity){
        return userRepository.save(tbUserEntity);
    }

    public TbUserEntity newTempUser(String username, TbRoomEntity tbRoomEntity){
        if(userRepository.existsByUserNameAndIsValid(username,true)){
            TbUserEntity tbUserEntity=new TbUserEntity();
            tbUserEntity.setUserName("Already exists");
            return tbUserEntity;
        }
        return createUser(username, MMBConstants.Default_Password,false,true,tbRoomEntity);
    }

    public ResponseEntity<?> AutoSignByInviteCode(String inviteCode){
       TbUserEntity tbUserEntity= userRepository.findTbUserEntityByInviteCode(inviteCode).orElse(new TbUserEntity());
       if(tbUserEntity.isValid()){
           return new ResponseEntity<>("User No need active", HttpStatus.OK);
       }
       else{
           tbUserEntity.setValid(true);
           userRepository.save(tbUserEntity);
           return new ResponseEntity<>("User has activate successfully", HttpStatus.OK);
       }
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
