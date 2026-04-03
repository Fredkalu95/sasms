package com.sasms.controller;

import com.sasms.model.dto.BookingRequest;
import com.sasms.model.dto.BookingResponse;
import com.sasms.model.entity.User;
import com.sasms.service.BookingService;
import com.sasms.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    // --- Create Booking ---
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public BookingResponse createBooking(@RequestBody BookingRequest request, Authentication auth) {
        User user = userService.findByEmail(auth.getName()); // get logged-in user
        return bookingService.createBooking(request, user);
    }

    // --- Get My Bookings ---
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<BookingResponse> getMyBookings(Authentication auth) {
        User user = userService.findByEmail(auth.getName());
        return bookingService.getUserBookings(user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingRequest>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BookingRequest> bookings = bookingService.getAllBookings(page, size);
        return ResponseEntity.ok(bookings);
    }


    // --- Update Booking ---
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookingResponse updateBooking(@PathVariable Long id, @RequestBody BookingRequest request) {
        return bookingService.updateBooking(id, request);
    }

    // --- Cancel Booking ---
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookingResponse cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}
