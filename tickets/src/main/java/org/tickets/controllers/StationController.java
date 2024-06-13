package org.tickets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tickets.models.Station;
import org.tickets.repository.StationRepository;
import org.tickets.services.Impl.StationServiceImpl;
import org.tickets.services.StationService;

import java.util.List;

@RequestMapping("/api/stations")
@RestController
public class StationController {
    private final StationService service;

    @Autowired
    public StationController(StationRepository stationRepository) {
        this.service = new StationServiceImpl(stationRepository);
    }

    @GetMapping
    public List<Station> getAllStations() {
        return service.all();
    }

    @PostMapping
    public ResponseEntity<Station> createStation(@RequestBody Station station) {
        Station savedStation = service.save(station);

        return ResponseEntity.ok(savedStation);
    }
}
