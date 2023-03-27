package com.example.myseckill.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author xqxls
 * @since 2023-03-07
 */
@ApiModel(value = "User对象", description = "后台用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("头像")
    private String head;

    @ApiModelProperty("盐")
    private String slat;

    @ApiModelProperty("创建时间")
    private Date registerDate;

    @ApiModelProperty("最后登录时间")
    private Date lastLoginDate;

    @ApiModelProperty("登录次数")
    private Integer loginCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
    public String getSlat() {
        return slat;
    }

    public void setSlat(String slat) {
        this.slat = slat;
    }
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", nickname=" + nickname +
            ", password=" + password +
            ", head=" + head +
            ", slat=" + slat +
            ", registerDate=" + registerDate +
            ", lastLoginDate=" + lastLoginDate +
            ", loginCount=" + loginCount +
        "}";
    }
}
