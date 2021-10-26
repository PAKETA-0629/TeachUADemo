package com.example.demo.service;

import com.example.demo.model.OrderedRooms;
import com.example.demo.model.Orders;
import com.example.demo.repository.OrderedRoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderedRoomsService {
    private final OrderedRoomsRepository orderedRoomsRepository;

    @Autowired
    public OrderedRoomsService(OrderedRoomsRepository orderedRoomsRepository) {

        this.orderedRoomsRepository = orderedRoomsRepository;
    }

    public List<OrderedRooms> findAll() {
        return orderedRoomsRepository.findAll();
    }
    public void save(String since, String until, Long id) {
        orderedRoomsRepository.insertValues(since, until, id);
    }

}
