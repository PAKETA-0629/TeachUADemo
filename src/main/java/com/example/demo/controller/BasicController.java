package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.model.OrderedRooms;
import com.example.demo.model.Room;
import com.example.demo.model.User;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BasicController {

    private final HotelService hotelService;
    private final RoomService roomService;
    private final OrderedRoomsService orderedRoomsService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public BasicController(HotelService hotelService, RoomService roomService, OrderedRoomsService orderedRoomsService, OrderService orderService, UserService userService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.orderedRoomsService = orderedRoomsService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/book")
    public String getBookPage() {
        return "book";
    }

    @PostMapping("/book")
    public String book(@RequestParam(value = "hotelName", required = true) String hotelName,
                       @RequestParam(value = "since", required = true) String since,
                       @RequestParam(value = "until", required = true) String until,
                       @RequestParam(value = "roomNumber", required = true) Long roomNumber) {

        if (!getCheck(hotelName, since, until, roomNumber).equals("available")) return "redirect:/error";
        Room room = roomService.findAll().stream().filter(room1 -> room1.getHotelName().equals(hotelName)).filter(room1 -> room1.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
        Long roomId = room.getId();
        orderedRoomsService.save(since, until, roomId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Long orderedRoomId = orderedRoomsService.findAll().stream().filter(orderedRooms1 -> orderedRooms1.getRoomId().equals(roomId)).filter(orderedRooms -> orderedRooms.getSince().equals(since)).filter(orderedRooms -> orderedRooms.getUntil().equals(until)).findAny().get().getId();
        Long userId = userService.findAll().stream().filter(user1 -> user1.getEmail().equals(username)).findFirst().get().getId();
        orderService.save(orderedRoomId, userId);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home-page";
    }

    @GetMapping("/hotels")
    public String finaAllOrderByName(Model model) {
        List<Hotel> hotels = hotelService.getOrderedByName();
        model.addAttribute("hotels", hotels);
        return "hotels";
    }

    @GetMapping("/check")
    public String getCheckForm() {
        return "check";
    }

    @PostMapping("/check")
    public String getCheck(@RequestParam(value = "hotelName", required = true) String hotelName,
                           @RequestParam(value = "since", required = true) String since,
                           @RequestParam(value = "until", required = true) String until,
                           @RequestParam(value = "roomNumber", required = true) Long roomNumber) {

        Date userDateSince = null;
        Date userDateUntil = null;
        try {
            userDateSince = new SimpleDateFormat("yyyy-MM-dd").parse(since);
            userDateUntil = new SimpleDateFormat("yyyy-MM-dd").parse(until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userDateSince == null | userDateUntil == null) return "redirect:/error";
        if (userDateSince.after(userDateUntil)) return "redirect:/error";

        Room userRoom = roomService.findAll().stream().filter(room -> room.getHotelName().equals(hotelName)).filter(room -> room.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
        if (userRoom == null) return "redirect:/error";
        List<OrderedRooms> orderedRooms = orderedRoomsService.findAll().stream().filter(orderedRooms1 -> orderedRooms1.getRoomId().equals(userRoom.getId())).collect(Collectors.toList());
        if (orderedRooms.size() == 0) return "available";
        boolean isAvailable = false;
        for (OrderedRooms orderedRoom : orderedRooms) {
            Date roomDateSince = null;
            Date roomDateUntil = null;
            try {
                roomDateSince = new SimpleDateFormat("yyyy-MM-dd").parse(orderedRoom.getSince());
                roomDateUntil = new SimpleDateFormat("yyyy-MM-dd").parse(orderedRoom.getUntil());
            } catch (ParseException e) {
                e.printStackTrace();
            }
             isAvailable = checkDates(userDateSince, userDateUntil, roomDateSince, roomDateUntil);
            if (!isAvailable) break;
        }

        if (isAvailable) return "available";
        else return "notAvailable";
    }

    private static boolean checkDates(Date userDateSince, Date userDateUntil, Date roomDateSince, Date roomDateUntil) {

        if (userDateUntil.before(roomDateSince) && userDateSince.after(new Date())) ;
        else if (userDateSince.equals(roomDateSince) | userDateUntil.equals(roomDateUntil)) return false;
        else if (userDateSince.after(roomDateSince) && userDateUntil.before(roomDateUntil)) return false;
        else if (userDateSince.before(roomDateSince) && userDateUntil.after(roomDateSince) && userDateUntil.before(roomDateUntil)) return false;
        else if (userDateSince.after(roomDateUntil)) ;
        return true;
    }



    @GetMapping("/search")
    public String findByName(@RequestParam(value = "country", required = false) String country, Model model) {

        List<Hotel> hotels = hotelService.findAll().stream().filter(hotel -> hotel.getCountry().equals(country)).collect(Collectors.toList());
        if (hotels.size() == 0) return "redirect:/";
        model.addAttribute("hotels", hotels);
        return "/hotels";
    }
}
