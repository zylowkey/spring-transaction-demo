package com.goldcard.sprinngtransactiondemo.dao;

import com.goldcard.sprinngtransactiondemo.pojo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    List findByName(String name);
}
