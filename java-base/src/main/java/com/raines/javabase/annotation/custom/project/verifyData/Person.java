package com.raines.javabase.annotation.custom.project.verifyData;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class Person implements Serializable {
    @NotEmpty
    private Integer id;
    @NotEmpty
    private int age;
    @NotEmpty
    private String name;
    @NotEmpty
    private String sex;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
