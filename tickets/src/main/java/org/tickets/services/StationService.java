package org.tickets.services;

import org.tickets.models.Order;
import org.tickets.models.Station;

import java.util.List;

public interface StationService {
    List<Station> all();

    Station get(Long id);

    Station save(Station station);
}
