package com.example.demo.repository;

import com.example.demo.model.OrderedRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface OrderedRoomsRepository extends JpaRepository<OrderedRooms, Long> {

    @Modifying
    @Query(value = "INSERT INTO orderedrooms(since, until, room_id) VALUES(to_timestamp(:since, 'YYYY-MM-DD'), to_timestamp(:until, 'YYYY-MM-DD'), :room_id)", nativeQuery = true)
    @Transactional
    public void insertValues(@Param("since") String since, @Param("until") String until, @Param("room_id") Long roomId);

}
