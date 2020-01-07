package com.zcs.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//用户实体类
@Entity                                       //和数据库对应生成
@Table(name = "t_user")                       //指定table名字
public class User {

    @Id                                        //标识ID代表是主键
    @GeneratedValue                            //ID的生成策略，自动生成
    private Long id;                           //主键ID
    private String nickname;                   //昵称
    private String username;                   //用户名
    private String password;                   //用户密码
    private String email;                       //邮箱
    private String avatar;                      //头像
    private Integer type;                       //分类
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;                    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;                    //更新时间

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();

    public User() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
