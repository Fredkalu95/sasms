package com.sasms.service;

import com.sasms.model.dto.BookingRequest;
import com.sasms.model.dto.BookingResponse;
import com.sasms.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    // Create a booking and return DTO
    BookingResponse createBooking(BookingRequest request, User user);

    // Get all bookings for a specific user
    List<BookingResponse> getUserBookings(User user);

    // Get all bookings (admin only)
    Page<BookingRequest> getAllBookings(int page, int size);

    // Update booking by ID
    BookingResponse updateBooking(Long bookingId, BookingRequest request);

    // Cancel booking by ID
    BookingResponse cancelBooking(Long bookingId);
}
