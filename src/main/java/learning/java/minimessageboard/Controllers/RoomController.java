package learning.java.minimessageboard.Controllers;

import jakarta.validation.Valid;
import learning.java.minimessageboard.Entities.TbRoomEntity;
import learning.java.minimessageboard.Services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @PostMapping("/findAll/{page}")
    public Page<TbRoomEntity> findAll(@PathVariable("page") int page){
        PageRequest pageRequest=PageRequest.of(page-1,10);
        return roomServices.findAll(pageRequest);
    }

    @DeleteMapping("/deleteRoom")
    public void deleteRoom(@RequestParam Long id) {
        roomServices.deleteRoom(id);
    }

    @DeleteMapping("/deleteRooms")
    public void deleteRooms(@RequestParam Long[] ids) {
        roomServices.deleteRooms(Arrays.stream(ids).toList());
    }
}
