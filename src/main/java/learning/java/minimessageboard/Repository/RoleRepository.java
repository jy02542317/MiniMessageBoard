package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbRoleEntity;
import learning.java.minimessageboard.Entities.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<TbRoleEntity, Long> {
    Optional<TbRoleEntity> findByRoleName(String name);
}
