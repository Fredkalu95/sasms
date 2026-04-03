package com.sasms.service.impl;

import com.sasms.model.dto.BookingRequest;
import com.sasms.model.dto.BookingResponse;
import com.sasms.model.entity.Booking;
import com.sasms.model.entity.Status;
import com.sasms.model.entity.User;
import com.sasms.repository.BookingRepository;
import com.sasms.service.BookingService;
import com.sasms.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

    public BookingServiceImpl(BookingRepository bookingRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    // --- Create Booking from DTO ---
    @Override
    public BookingResponse createBooking(BookingRequest request, User user) {

        if (request.getStartTime().isAfter(request.getEndTime()) ||
                request.getStartTime().isEqual(request.getEndTime())) {
            throw new IllegalArgumentException("Booking startTime must be before endTime");
        }

        // Check for overlapping bookings for this user
        List<Booking> userBookings = bookingRepository.findByUser(user);
        boolean overlap = userBookings.stream().anyMatch(b ->
                b.getStatus() != Status.CANCELLED && // ignore cancelled bookings
                        request.getStartTime().isBefore(b.getEndTime()) &&
                        request.getEndTime().isAfter(b.getStartTime())
        );

        if (overlap) {
            throw new IllegalArgumentException("Booking overlaps with an existing booking");
        }

        Booking booking = Booking.builder()
                .title(request.getTitle())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .description(request.getDescription())
                .status(Status.PENDING)
                .user(user)
                .build();

        booking = bookingRepository.save(booking);
        notificationService.sendNotification(
                user,
                "Booking Created",
                "Your booking '" + booking.getTitle() + "' has been created successfully."
        );

        return mapToResponse(booking);
    }

    // --- Get bookings for a user ---
    @Override
    public List<BookingResponse> getUserBookings(User user) {
        return bookingRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --- Get all bookings (for admin) ---
    @Override
    public Page<BookingRequest> getAllBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findAll(pageable)
                .map(booking -> new BookingRequest(
                        booking.getTitle(),
                        booking.getDescription(),
                        booking.getStartTime(),
                        booking.getEndTime()
                ));
    }

    // --- Update booking ---
    @Override
    public BookingResponse updateBooking(Long bookingId, BookingRequest request) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        existing.setTitle(request.getTitle());
        existing.setStartTime(request.getStartTime());
        existing.setEndTime(request.getEndTime());
        existing.setDescription(request.getDescription());

        bookingRepository.save(existing);
        return mapToResponse(existing);
    }

    // --- Cancel booking ---
    @Override
    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(Status.CANCELLED);
        bookingRepository.save(booking);
        return mapToResponse(booking);
    }

    // --- Helper: Convert entity to DTO ---
    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setTitle(booking.getTitle());
        response.setStartTime(booking.getStartTime());
        response.setEndTime(booking.getEndTime());
        response.setDescription(booking.getDescription());
        response.setStatus(booking.getStatus().name());
        response.setUserEmail(booking.getUser().getEmail());
        return response;
    }
}
