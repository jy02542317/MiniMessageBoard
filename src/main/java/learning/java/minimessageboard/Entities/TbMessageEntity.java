package learning.java.minimessageboard.Entities;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbMessage")
public class TbMessageEntity extends BaseEntity {

    @Basic
    @Column(name = "Title", length = 50)
    private String title;
    @Basic
    @Column(name = "Message",  length = 250)
    private String message;
    @Basic
    @Column(name = "RoomId")
    private Integer roomId;


}
