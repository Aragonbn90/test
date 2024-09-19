package com.edpham.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edpham.test.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, String> {
    
    @Query("SELECT a.name FROM Airport a WHERE a.isoCountry = :countryCode")
    List<String> findAirportNamesByIsoCountry(@Param("countryCode") String countryCode);

    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(latitude)))) AS distance " +
            "FROM Airport " +
            "ORDER BY distance " +
            "LIMIT 5", nativeQuery = true)
    List<Airport> findNearestAirports(@Param("latitude") double latitude, @Param("longitude") double longitude);
}