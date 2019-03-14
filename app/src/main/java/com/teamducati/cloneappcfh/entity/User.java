package com.teamducati.cloneappcfh.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String imgAvatarUrl;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String birthday;
    private String email;
    private String address;
    private String gender;
    private String phoneNumber;
}
