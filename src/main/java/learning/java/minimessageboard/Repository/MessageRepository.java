package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<TbMessageEntity, Long> {
    public Page<TbMessageEntity> findAll(Pageable pageable);
}
