package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.BookingTime;
import ru.practicum.shareit.comment.model.CommentDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Items")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "NAME")
    String name;
    @Column(name = "DESCRIPTION")
    String description;
    @Column(name = "IS_AVAILABLE")
    Boolean available;
    @Column(name = "OWNER_ID")
    Long owner;
    @Column(name = "REQUEST_ID")
    Long requestId;
    @Transient
    BookingTime lastBooking;
    @Transient
    BookingTime nextBooking;
    @Transient
    List<CommentDto> comments;
}
