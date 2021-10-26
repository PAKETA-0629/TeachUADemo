package com.example.demo.repository;

import com.example.demo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query(value = "SELECT * FROM hotels ORDER BY name", nativeQuery = true)
    public List<Hotel> getHotelsOrderedByName();
}
