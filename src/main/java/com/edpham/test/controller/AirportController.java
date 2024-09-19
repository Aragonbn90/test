package com.edpham.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edpham.test.model.Airport;
import com.edpham.test.service.AirportService;

@RestController
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/api/airports/by-country")
    public List<String> getAirportNamesByCountryCode(@RequestParam String countryCode) {
        return airportService.getAirportNamesByCountryCode(countryCode);
    }

    @GetMapping("/api/airports/nearest")
    public List<Airport> getNearestAirports(@RequestParam double latitude, @RequestParam double longitude) {
        return airportService.getNearestAirports(latitude, longitude);
    }
}