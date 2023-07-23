package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TbUserEntity, Long> {

    Boolean existsByUserName(String username);

    Boolean existsByUserNameAndIsValid(String username,Boolean IsValid);

    Optional<TbUserEntity> findTbUserEntityByInviteCode(String InviteCode);
    Optional<TbUserEntity> findTbUserEntityByUserName(String UserName);

    Optional<TbUserEntity> findTbUserEntityByUserNameAndIsValid(String UserName,Boolean IsValid);

    Optional<TbUserEntity> findByUserNameOrEmailAndIsValid(String username,String email,Boolean IsValid);

}
