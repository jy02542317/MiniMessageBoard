package learning.java.minimessageboard.Entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbRoom")
public class TbRoomEntity extends BaseEntity {

    @Basic
    @Column(name = "RoomName", nullable = false, length = 50)
    private String roomName;



}
