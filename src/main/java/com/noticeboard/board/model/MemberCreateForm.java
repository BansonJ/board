package com.noticeboard.board.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Entity
public class MemberCreateForm {
    @Id
    @NotEmpty(message = "필수입니다.")
    private String id;
    @NotEmpty(message = "필수입니다.")
    private String name;
    @NotEmpty(message = "필수입니다.")
    private String email;
    @NotEmpty(message = "필수입니다.")
    private String passwd;
    @NotEmpty(message = "필수입니다.")
    private String passwd2;
    @NotEmpty(message = "필수입니다.")
    private String nickname;
    @NotEmpty(message = "필수입니다.")
    private  String phone;

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

    public String getPasswd2() {
        return passwd2;
    }

    public void setPasswd2(String passwd2) {
        this.passwd2 = passwd2;
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
