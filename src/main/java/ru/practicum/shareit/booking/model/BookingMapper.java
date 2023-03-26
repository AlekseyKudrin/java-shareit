package ru.practicum.shareit.booking.model;


import ru.practicum.shareit.booking.model.enums.BookingStatus;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                1L,
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus()
        );
    }

    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                bookingDto.getItem(),
                bookingDto.getBooker(),
                BookingStatus.WAITING
        );
    }

    public static BookingTime toBookingTime(Booking booking) {
        return new BookingTime(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }
}
