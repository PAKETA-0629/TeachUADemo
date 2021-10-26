package com.example.demo.service;
import com.example.demo.model.UsersAndOrdersTemp;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempService {

    private final TempRepository tempRepository;

    @Autowired
    public TempService(TempRepository tempRepository) {
        this.tempRepository = tempRepository;
    }

    public List<UsersAndOrdersTemp> findAll() {
        return tempRepository.findAll();
    }
}
