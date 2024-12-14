package net.kamal.carservices.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.kamal.carservices.enums.UserType;

@Getter @Setter @ToString
public class Users {
    private Long id_user;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String cin;
    private String email;
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    private UserType status;
}