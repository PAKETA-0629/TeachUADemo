package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.model.Room;
import com.example.demo.model.UsersAndOrdersTemp;
import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ManagerController {

    private final RoomService roomService;
    private final HotelService hotelService;
    private final UserService userService;
    private final OrderService orderService;
    private final TempService tempService;

    ManagerController(RoomService roomService, HotelService hotelService, UserService userService, OrderService orderService, TempService tempService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
        this.userService = userService;
        this.orderService = orderService;
        this.tempService = tempService;
    }

    @GetMapping("/room-create")
    public String createRoomForm(Room room) {
        return "room-create";
    }

    @PostMapping("/room-create")
    public String createRoom(Room room) {
        roomService.saveRoom(room);
        return "redirect:/hotels";
    }

    @GetMapping("/hotel-create")
    public String createHotelForm(Hotel hotel) {
        return "hotel-create";
    }

    @PostMapping("/hotel-create")
    public String createHotel(Hotel hotel) {
        hotelService.saveHotel(hotel);
        return "redirect:/hotels";
    }

    @GetMapping("/users")
    public String getByNameForm(Model model) {
        List<UsersAndOrdersTemp> temps = tempService.findAll();
        System.out.println(temps);
        model.addAttribute("temps", temps);
        return "users";
    }
}