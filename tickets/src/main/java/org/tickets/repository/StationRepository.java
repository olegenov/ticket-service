package org.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tickets.models.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
}
