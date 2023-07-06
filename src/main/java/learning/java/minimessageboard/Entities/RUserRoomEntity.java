package learning.java.minimessageboard.Entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "r_user_room", schema = "dbo", catalog = "MiniMessageBoard")
public class RUserRoomEntity {
    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic
    @Column(name = "room_id", nullable = false)
    private int roomId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RUserRoomEntity that = (RUserRoomEntity) o;
        return userId == that.userId && roomId == that.roomId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roomId);
    }
}
