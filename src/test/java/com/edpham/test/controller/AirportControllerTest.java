package com.edpham.test.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.edpham.test.model.Airport;
import com.edpham.test.service.AirportService;

@WebMvcTest(AirportController.class)
public class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @Test
    public void testGetAirportNamesByCountryCode() throws Exception {
        List<String> airportNames = Arrays.asList("Los Angeles International Airport", "Heathrow Airport");
        Mockito.when(airportService.getAirportNamesByCountryCode(anyString())).thenReturn(airportNames);

        mockMvc.perform(get("/api/airports/by-country")
                .param("countryCode", "US"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Los Angeles International Airport\", \"Heathrow Airport\"]"));
    }

    @Test
    public void testGetNearestAirports() throws Exception {
        List<Airport> airports = Arrays.asList(
                new Airport("1", "large_airport", "Los Angeles International Airport", 125, "NA", "US", "US-CA", "Los Angeles", "LAX", "KLAX", "LAX", 33.9425, -118.4081),
                new Airport("2", "large_airport", "Heathrow Airport", 83, "EU", "GB", "GB-ENG", "London", "LHR", "EGLL", "LHR", 51.4706, -0.461941)
        );
        Mockito.when(airportService.getNearestAirports(anyDouble(), anyDouble())).thenReturn(airports);

        mockMvc.perform(get("/api/airports/nearest")
                .param("latitude", "34.0522")
                .param("longitude", "-118.2437"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"type\":\"large_airport\",\"name\":\"Los Angeles International Airport\",\"elevationFt\":125,\"continent\":\"NA\",\"isoCountry\":\"US\",\"isoRegion\":\"US-CA\",\"municipality\":\"Los Angeles\",\"gpsCode\":\"LAX\",\"iataCode\":\"KLAX\",\"localCode\":\"LAX\",\"latitude\":33.9425,\"longitude\":-118.4081},{\"id\":\"2\",\"type\":\"large_airport\",\"name\":\"Heathrow Airport\",\"elevationFt\":83,\"continent\":\"EU\",\"isoCountry\":\"GB\",\"isoRegion\":\"GB-ENG\",\"municipality\":\"London\",\"gpsCode\":\"LHR\",\"iataCode\":\"EGLL\",\"localCode\":\"LHR\",\"latitude\":51.4706,\"longitude\":-0.461941}]"));
    }
}