package com.zcs.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//博客标签实体类
@Entity                          //和数据库对应生成
@Table(name = "t_tag")              //指定table名字
public class Tag {

    @Id                             //标识ID代表是主键
    @GeneratedValue                 //ID的生成策略，自动生成
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")  //多对多
    private List<Blog> blogs = new ArrayList<>();



    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }


    @Override
    public String toString() {
        return "TagRepository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
