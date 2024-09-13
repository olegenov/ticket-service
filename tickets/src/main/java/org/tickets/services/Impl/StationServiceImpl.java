package org.tickets.services.Impl;

import org.springframework.stereotype.Service;
import org.tickets.models.Order;
import org.tickets.models.Station;
import org.tickets.repository.StationRepository;
import org.tickets.services.OrderService;
import org.tickets.services.StationService;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<Station> all() {
        return stationRepository.findAll();
    }

    @Override
    public Station get(Long id) {
        return stationRepository.getReferenceById(id);
    }

    @Override
    public Station save(Station station) {
        return stationRepository.save(station);
    }
}
