package learning.java.minimessageboard.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbFile")
public class TBFileEntity extends BaseEntity{

    @Basic
    @Column(name = "FileName", length = 250)
    private String filename;

    @Basic
    @Column(name = "Extension", length = 10)
    private String extension;

    @ManyToOne
    @JoinColumn(name = "Message_Id")
    private TbMessageEntity tbMessageEntity;
}
