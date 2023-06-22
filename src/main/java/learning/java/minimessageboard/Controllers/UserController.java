package learning.java.minimessageboard.Controllers;

import jakarta.validation.Valid;
import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {
    @Autowired
    private UserServices userServices;
    @GetMapping("/findAll")
    public List<TbUserEntity> findAll() {
        return userServices.findAll();
    }
    @PostMapping("/Login")
    public List<TbUserEntity> Login(@Valid @RequestBody TbUserEntity tbUserEntity) {
        return userServices.ExistUser(tbUserEntity);
    }

    @PostMapping("/SaveUser")
    public TbUserEntity createEmployee(@Valid @RequestBody TbUserEntity tbUserEntity) {
        return userServices.SaveUser(tbUserEntity);
    }
}
