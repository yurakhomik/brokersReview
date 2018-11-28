package com.example.brokers.broker;

import com.example.brokers.comment.Comment;
import com.example.brokers.mark.Mark;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Brokers")
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "age")
    private int age;

    @Column(name = "rating")
    private double rating;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "broker_id")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="broker_id")
    private Set<Mark> marks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getRating() {
        double i = 0.0;
        for (Mark m : marks) {
            i+= m.getRating();
        }
        return i/marks.size();
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }
}
