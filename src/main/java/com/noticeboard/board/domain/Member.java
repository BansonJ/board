package com.noticeboard.board.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Member {

    @Id
    @NotEmpty(message = "필수입니다.")
    @Column(unique = true)
    private String nickname;

    @NotEmpty(message = "필수입니다.")
    @Column(unique = true)
    private String id;
    @NotEmpty(message = "필수입니다.")
    private String name;
    @NotEmpty(message = "필수입니다.")
    private String email;
    @NotEmpty(message = "필수입니다.")
    private String passwd;
    @NotEmpty(message = "필수입니다.")
    private String phone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
