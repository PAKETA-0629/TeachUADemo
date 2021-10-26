package com.example.demo.repository;

import com.example.demo.model.UsersAndOrdersTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TempRepository extends JpaRepository<UsersAndOrdersTemp, Long> {

    @Override
    @Query(value = "SELECT * FROM users_and_orders;", nativeQuery = true)
    List<UsersAndOrdersTemp> findAll();

}
