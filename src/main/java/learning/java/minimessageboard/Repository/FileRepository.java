package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TBFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<TBFileEntity, Long> {
}
