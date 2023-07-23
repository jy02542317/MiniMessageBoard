package learning.java.minimessageboard.Repository;

import learning.java.minimessageboard.Entities.TbRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<TbRoomEntity, Long> {
     Page<TbRoomEntity> findAll(Pageable pageable);

    @Modifying
    @Query(value="insert into [r_user_room] select a.id, ?1 from tbUser a inner join r_user_role b on a.id=b.user_id where b.role_id=1 ", nativeQuery = true)
     void updateRelationAllAdmin(Long room_id);
}
