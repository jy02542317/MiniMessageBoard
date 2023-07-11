package learning.java.minimessageboard.Entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbRole")
public class TbRoleEntity extends BaseEntity {

    @Basic
    @Column(name = "RoleName", length = 50)
    private String roleName;
}
