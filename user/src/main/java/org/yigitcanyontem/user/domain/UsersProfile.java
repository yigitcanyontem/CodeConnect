package org.yigitcanyontem.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.yigitcanyontem.clients.users.dto.UserRegisterDTO;
import org.yigitcanyontem.clients.users.enums.Role;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "users_profile",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_id_unique",
                        columnNames = "usersId"
                )
        }
)
@Builder
public class UsersProfile {
    @Id
    @SequenceGenerator(
            name = "users_id_sequence",
            sequenceName = "users_profile_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_profile_id_sequence"
    )
    private Integer id;

    @OneToOne()
    @JoinColumn(
            name = "usersId",
            nullable = false
    )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Users usersId;

    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String bio;
    private String city;
    private String country;
    private String website;
    private String jobTitle;
    private Date birthDate;
    private Date createdAt;
    private String linkedinProfile;
    private String githubProfile;
    private String mediumProfile;
}