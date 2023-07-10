package learning.java.minimessageboard.Controllers;

import jakarta.validation.Valid;
import learning.java.minimessageboard.Entities.TbRoomEntity;
import learning.java.minimessageboard.Services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Room")
public class RoomController {
    @Autowired
    private RoomServices roomServices;

    @GetMapping("/findAll")
    public List<TbRoomEntity> findAll(){
        return roomServices.findAll();
    }

    @PostMapping("/saveRoom")
    public TbRoomEntity saveRoom(@Valid @RequestBody TbRoomEntity tbRoomEntity){
        return roomServices.saveRoom(tbRoomEntity);
    }


    @PostMapping("/findById")
    public TbRoomEntity findById(@RequestParam Long id){
        return roomServices.getRoomById(id);
    }


}