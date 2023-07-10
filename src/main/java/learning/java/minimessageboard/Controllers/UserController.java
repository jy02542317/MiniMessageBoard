package learning.java.minimessageboard.Controllers;

import jakarta.validation.Valid;
import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public Boolean Login(@Valid @RequestBody TbUserEntity tbUserEntity) {
        List<TbUserEntity> list = userServices.ExistUser(tbUserEntity);
        if (list.size() > 0)
            return true;
        else return false;
    }

    @PostMapping("/SaveUser")
    public TbUserEntity createOrSaveEmployee(@Valid @RequestBody TbUserEntity tbUserEntity) {
        return userServices.SaveUser(tbUserEntity);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam Long id) {
        userServices.DeleteUser(id);
    }

    @DeleteMapping("/deleteUsers")
    public void deleteUsers(@RequestParam Long[] ids) {
        userServices.DeleteUsers(Arrays.stream(ids).toList());
    }

    @PostMapping("/getUserById")
    public TbUserEntity getUserById(@RequestParam Long id) {
        return userServices.getTbUserById(id).get();
    }
}
