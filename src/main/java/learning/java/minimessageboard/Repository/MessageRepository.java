package learning.java.minimessageboard.Repository;

import jakarta.annotation.Nullable;
import learning.java.minimessageboard.Entities.TbMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<TbMessageEntity, Long>,JpaSpecificationExecutor<TbMessageEntity> {


    Page<TbMessageEntity> findAll(@Nullable Specification<TbMessageEntity> spec,Pageable pageable);

}
