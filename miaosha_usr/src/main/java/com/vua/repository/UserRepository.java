package com.vua.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vua.entity.User;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 16:10
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByNameAndPassword(String name,String password);
}
