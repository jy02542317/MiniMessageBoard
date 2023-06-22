package learning.java.minimessageboard.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id", nullable = false)
    private int id;
    @Basic
    @Column(name = "IsValid", nullable = false)
    private boolean isValid;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonIgnore
    @Column(name = "CreateTime", updatable = false)
    private Date createTime;
    @Basic
    @JsonIgnore
    @Column(name = "CreateUser")
    @CreatedBy
    private Integer createUser;
    @Basic
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "ModifyTime")
    private Date modifyTime;
    @Basic
    @JsonIgnore
    @LastModifiedBy
    @Column(name = "ModifyUser")
    private Integer modifyUser;
}
