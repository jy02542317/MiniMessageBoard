package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbRoleEntity;
import learning.java.minimessageboard.Entities.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<TbRoleEntity, Long> {
    Optional<TbRoleEntity> findByRoleName(String name);
}
