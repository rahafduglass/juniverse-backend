package org.example.chatbackend.persistance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.UserRole;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="fullname")
    private String fullname;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="user_role")
    private UserRole userRole;

    @Column(name="year_of_study")
    private int yearOfStudy;

    @Column(name="major")
    private String major;

    @Column(name="bio")
    private String bio;

    @Column(name="is_therapist")
    private boolean isTherapist; // therapist is ONE , we have only one therapist

}
