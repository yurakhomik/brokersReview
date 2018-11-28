package com.example.brokers.mark;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "broker_id")
    private Long brokerId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_full_name")
    private String userFullName;

    public Mark() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return rating == mark.rating &&
                Objects.equals(id, mark.id) &&
                Objects.equals(brokerId, mark.brokerId) &&
                Objects.equals(userId, mark.userId) &&
                Objects.equals(userFullName, mark.userFullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, brokerId, userId, userFullName);
    }
}
