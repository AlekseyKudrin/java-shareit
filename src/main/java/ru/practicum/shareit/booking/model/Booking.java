package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "START_DATE")
    private LocalDateTime start;
    @Column(name = "END_DATE")
    private LocalDateTime end;
    @OneToOne
    @JoinColumn(
            name = "ITEM_ID",
            referencedColumnName = "ID"
    )
    private Item item;
    @OneToOne
    @JoinColumn(
            name = "BOOKER_ID",
            referencedColumnName = "ID"
    )
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookingStatus    status;
}
