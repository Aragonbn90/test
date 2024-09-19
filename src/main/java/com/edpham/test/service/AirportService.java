package com.edpham.test.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edpham.test.model.Airport;
import com.edpham.test.repository.AirportRepository;
import com.opencsv.CSVReader;

import jakarta.annotation.PostConstruct;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @PostConstruct
    public void init() {
        loadCSVData();
    }

    public void loadCSVData() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/airport-codes.csv")));
             CSVReader csvReader = new CSVReader(reader)) {
            List<Airport> airports = csvReader.readAll().stream().skip(1).map(fields -> {
                if (fields.length < 11) {
                    System.err.println("Invalid line: " + String.join(",", fields));
                    return null;
                }
                try {
                    Airport airport = new Airport();
                    airport.setId(fields[0].trim()); // ident
                    airport.setType(fields[1].trim());
                    airport.setName(fields[2].trim());
                    airport.setElevationFt(fields[3].trim().isEmpty() ? 0 : Integer.parseInt(fields[3].trim())); // elevation_ft
                    airport.setContinent(fields[4].trim());
                    airport.setIsoCountry(fields[5].trim());
                    airport.setIsoRegion(fields[6].trim());
                    airport.setMunicipality(fields[7].trim());
                    airport.setGpsCode(fields[8].trim());
                    airport.setIataCode(fields[9].trim());
                    airport.setLocalCode(fields[10].trim());
                    if (fields.length > 12) {
                        String[] coordinates = fields[12].trim().split(",");
                        airport.setLatitude(Double.parseDouble(coordinates[0]));
                        airport.setLongitude(Double.parseDouble(coordinates[1]));
                    }
                    return airport;
                } catch (NumberFormatException e) {
                    System.err.println("Number format exception for line: " + String.join(",", fields));
                    e.printStackTrace();
                    return null;
                }
            }).filter(airport -> airport != null).collect(Collectors.toList());
            airportRepository.saveAll(airports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAirportNamesByCountryCode(String countryCode) {
        return airportRepository.findAirportNamesByIsoCountry(countryCode);
    }

    public List<Airport> getNearestAirports(double latitude, double longitude) {
        return airportRepository.findNearestAirports(latitude, longitude);
    }
}