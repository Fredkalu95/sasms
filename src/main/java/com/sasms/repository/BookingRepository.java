package com.sasms.repository;

import com.sasms.model.entity.Booking;
import com.sasms.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user); // Get bookings for a user
    Page<Booking> findAll(Pageable pageable);
}
