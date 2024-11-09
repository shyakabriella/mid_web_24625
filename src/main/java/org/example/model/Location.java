package org.example.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue
    private UUID location_id;

    @Column(nullable = false)
    private String location_code;

    @Column(nullable = false)
    private String location_name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parent;

    // Getters and setters
    public UUID getLocation_id() {
        return location_id;
    }

    public void setLocation_id(UUID location_id) {
        this.location_id = location_id;
    }

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }
}

enum LocationType {
    PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
}
