package com.golvia_waitlist.demo.entity;

import com.golvia_waitlist.demo.enumms.ProfileTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "waitlist")
public class Waitlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private ProfileTypes profileType;

    private LocalDateTime joinDate = LocalDateTime.now();

}
