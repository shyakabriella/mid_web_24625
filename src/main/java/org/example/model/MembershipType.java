package org.example.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "Membership_Type")
public class MembershipType {
    @Id
    @GeneratedValue
    private UUID membership_type_id;

    @Column(nullable = false)
    private String membership_name;

    @Column(nullable = false)
    private int max_books;

    @Column(nullable = false)
    private BigDecimal membership_price;

    // Getters and setters
    public UUID getMembership_type_id() {
        return membership_type_id;
    }

    public void setMembership_type_id(UUID membership_type_id) {
        this.membership_type_id = membership_type_id;
    }

    public String getMembership_name() {
        return membership_name;
    }

    public void setMembership_name(String membership_name) {
        this.membership_name = membership_name;
    }

    public int getMax_books() {
        return max_books;
    }

    public void setMax_books(int max_books) {
        this.max_books = max_books;
    }

    public BigDecimal getMembership_price() {
        return membership_price;
    }

    public void setMembership_price(BigDecimal membership_price) {
        this.membership_price = membership_price;
    }
}
