package com.example.demo.service;

import com.example.demo.model.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getOrderedByName() {
        return hotelRepository.getHotelsOrderedByName();
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll().stream().sorted(Comparator.comparing(Hotel::getId)).collect(Collectors.toList());
    }

    public Hotel findById(Long id) {
        return hotelRepository.findAll().stream().filter(hotel -> hotel.getId().equals(id)).findFirst().orElse(null);
    }

    public Hotel findByName(String name) {
        return hotelRepository.findAll().stream().filter(hotel -> hotel.getName().equals(name)).findFirst().orElse(null);
    }
}
