package learning.java.minimessageboard.Controllers;

import learning.java.minimessageboard.Entities.TbRoomEntity;
import learning.java.minimessageboard.Services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public TbRoomEntity saveRoom(TbRoomEntity tbRoomEntity){
        return roomServices.saveRoom(tbRoomEntity);
    }




}
