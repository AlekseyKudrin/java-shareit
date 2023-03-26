package ru.practicum.shareit.comment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENTS")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @ManyToOne
    @JoinColumn(
            name = "ITEM_ID",
            referencedColumnName = "ID"
    )
    Item item;
    @OneToOne
    @JoinColumn(
            name = "AUTHOR_ID",
            referencedColumnName = "ID"
    )
    User user;
    @Column(name = "created")
    LocalDateTime created;


    public Comment(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
