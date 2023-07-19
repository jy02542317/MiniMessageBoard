package learning.java.minimessageboard.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;


@Builder
@Data
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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "Room_Id")
    private TbRoomEntity tbRoomEntity;



    @OneToMany(mappedBy = "tbMessageEntity", fetch= FetchType.EAGER)
    private List<TBFileEntity> fileList;

}
