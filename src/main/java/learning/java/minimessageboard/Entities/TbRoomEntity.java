package learning.java.minimessageboard.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbRoom")
public class TbRoomEntity extends BaseEntity {

    @Basic
    @Column(name = "RoomName", nullable = false, length = 50)
    private String roomName;

    @OneToMany(mappedBy = "tbRoomEntity", fetch= FetchType.EAGER)
    private List<TbMessageEntity> messageList;

}
