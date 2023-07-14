package learning.java.minimessageboard.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbUser")
public class TbUserEntity extends BaseEntity{

    @Basic
    @Column(name = "UserName", nullable = true, length = 50)
    private String userName;
    @Basic
    @Column(name = "Email", nullable = true, length = 50)
    private String email;
    @Basic
    @Column(name = "PassWord", nullable = true, length = 50)
    private String passWord;

    @Basic
    @Column(name = "InViteCode", nullable = true, length = 50)
    private String inviteCode;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "r_user_role",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<TbRoleEntity> roleList;//角色

    @ManyToMany
    @JoinTable(name = "r_user_room",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<TbRoomEntity> roomList;//Room
}
