package learning.java.minimessageboard.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import learning.java.minimessageboard.Common.MMBConstants;
import learning.java.minimessageboard.Entities.TbRoomEntity;
import learning.java.minimessageboard.Entities.TbUserEntity;
import learning.java.minimessageboard.Services.RoomServices;
import learning.java.minimessageboard.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Tag(name = "2. 消息室接口", description = "MessageRoom的增删改查")
@RestController
@RequestMapping("/api/Room")
public class RoomController {
    @Autowired
    private RoomServices roomServices;

    @Autowired
    private UserServices userServices;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findAll")
    public List<TbRoomEntity> findAll() {
        return roomServices.findAll();
    }

    @Operation(summary = "1. 新增消息室", security = @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createRoom")
    public TbRoomEntity createRoom(@Valid @RequestBody TbRoomEntity tbRoomEntity) {
        return roomServices.createRoom(tbRoomEntity);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/findById")
    public TbRoomEntity findById(@RequestParam Long id) {
        return roomServices.getRoomById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/findAll/{page}")
    public Page<TbRoomEntity> findAll(@PathVariable("page") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return roomServices.findAll(pageRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteRoom")
    public void deleteRoom(@RequestParam Long id) {
        roomServices.deleteRoom(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteRooms")
    public void deleteRooms(@RequestParam Long[] ids) {
        roomServices.deleteRooms(Arrays.stream(ids).toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/inviteMemberToRoom")
    public TbUserEntity inviteMemberToRoom(@RequestParam Long userid, @RequestParam Long roomid) {
        TbUserEntity tbUserEntity = userServices.getTbUserById(userid).get();
        TbRoomEntity tbRoomEntity = roomServices.getRoomById(roomid);
        List<TbRoomEntity> list= tbUserEntity.getRoomList();
        if(list==null)
            list=new ArrayList<>();
        else if(!list.contains(tbRoomEntity))
            list.add(tbRoomEntity);
        return userServices.saveUser(tbUserEntity);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/inviteNewMemberToRoom")
    public String inviteNewMemberToRoom(@RequestParam String username, @RequestParam Long roomid) {
        TbRoomEntity tbRoomEntity = roomServices.getRoomById(roomid);
        TbUserEntity tbUserEntity = userServices.newTempUser(username,tbRoomEntity);
        if(tbUserEntity.getUserName().equals("Already Exists")){
            return "Already Exists";
        }
        else{
            return MMBConstants.AutoSignUpUrl+tbUserEntity.getInviteCode();
        }
    }
}
