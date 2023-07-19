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

    @Query(value = "select Top 1 a.* from TbUser a where (a.username= :username or a.Email=:email) and a.IsValid=:IsValid", nativeQuery = true)
    Optional<TbUserEntity> findByUserNameOrEmailAndIsValid(String username,String email,Boolean IsValid);

}
