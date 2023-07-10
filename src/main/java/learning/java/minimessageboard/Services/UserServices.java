package learning.java.minimessageboard.Services;

import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public Optional<TbUserEntity> getTbUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<TbUserEntity> findAll() {
        return userRepository.findAll();
    }

    public TbUserEntity SaveUser(TbUserEntity tbUserEntity) {
        return userRepository.save(tbUserEntity);
    }

    public void DeleteUsers(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<TbUserEntity> ExistUser(TbUserEntity tbUserEntity) {
        List<TbUserEntity> userEntityList = userRepository.findTbUserEntityByUserNameAndPassWordAndValid(tbUserEntity.getUserName(), tbUserEntity.getPassWord(), true);
       return userEntityList;
    }
}
