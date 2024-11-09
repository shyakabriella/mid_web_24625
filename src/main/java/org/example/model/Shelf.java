package org.example.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Shelf")
public class Shelf {
    @Id
    @GeneratedValue
    private UUID shelf_id;

    @Column(nullable = false)
    private int available_stock;

    @Column(nullable = false)
    private int borrowed_number;

    @Column(nullable = false)
    private int initial_stock;

    @Column(nullable = false)
    private String book_category;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Getters and setters
    public UUID getShelf_id() {
        return shelf_id;
    }

    public void setShelf_id(UUID shelf_id) {
        this.shelf_id = shelf_id;
    }

    public int getAvailable_stock() {
        return available_stock;
    }

    public void setAvailable_stock(int available_stock) {
        this.available_stock = available_stock;
    }

    public int getBorrowed_number() {
        return borrowed_number;
    }

    public void setBorrowed_number(int borrowed_number) {
        this.borrowed_number = borrowed_number;
    }

    public int getInitial_stock() {
        return initial_stock;
    }

    public void setInitial_stock(int initial_stock) {
        this.initial_stock = initial_stock;
    }

    public String getBook_category() {
        return book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
