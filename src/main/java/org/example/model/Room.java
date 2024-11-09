package org.example.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue
    private UUID room_id;

    @Column(nullable = false)
    private String room_code;

    // Getters and setters
    public UUID getRoom_id() {
        return room_id;
    }

    public void setRoom_id(UUID room_id) {
        this.room_id = room_id;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }
}
