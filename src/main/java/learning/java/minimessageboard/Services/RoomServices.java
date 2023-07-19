package learning.java.minimessageboard.Services;

import learning.java.minimessageboard.Entities.TbRoomEntity;
import learning.java.minimessageboard.Repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomServices {
    private final RoomRepository roomRepository;

    public RoomServices(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<TbRoomEntity> findAll(){
        return roomRepository.findAll();
    }

    public Page<TbRoomEntity> findAll(Pageable pageable){
        return roomRepository.findAll(pageable);
    }

    public TbRoomEntity getRoomById(Long Id){
        return roomRepository.findById(Id).get();
    }
    public Optional<TbRoomEntity> getOnlyRoomById(Long Id){
        return roomRepository.findById(Id);
    }

    public TbRoomEntity saveRoom(TbRoomEntity tbRoomEntity){
        return roomRepository.save(tbRoomEntity);
    }

    public void deleteRoom(Long Id){
        roomRepository.deleteById(Id);
    }

    public void deleteRooms(List<Long> ids){
        roomRepository.deleteAllById(ids);
    }
}
