package com.losung.assignment.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

}

