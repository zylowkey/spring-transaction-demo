package com.goldcard.sprinngtransactiondemo.pojo;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Data
@Table(name = "t_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public UserEntity() {
    }

    public UserEntity(String name) {
        this.name = name;
    }
}
