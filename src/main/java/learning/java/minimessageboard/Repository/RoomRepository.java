package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<TbRoomEntity, Long> {
    public Page<TbRoomEntity> findAll(Pageable pageable);
}
