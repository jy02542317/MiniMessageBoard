package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TbUserEntity, Long> {
    Optional<TbUserEntity> findByUserName(String username);
    Boolean existsByUserName(String username);

    @Query(value = "select a.* from TbUser a where a.username= :username and a.password=:Password and a.IsValid=:IsValid", nativeQuery = true)
    List<TbUserEntity> findTbUserEntityByUserNameAndPassWordAndValid(String username, String Password, Boolean IsValid);

    Optional<TbUserEntity> findByUserNameOrEmail(String username, String email);

}
