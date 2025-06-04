package com.booking.booking_service.repository;

import com.booking.booking_service.entity.SeatTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatTemplateRepository extends JpaRepository<SeatTemplate, Long> {

    List<SeatTemplate> findByScheme(Integer scheme);
}
