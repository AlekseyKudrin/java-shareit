package ru.practicum.shareit.comment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @ManyToOne
    @JoinColumn(
            name = "item_id",
            referencedColumnName = "id"
    )
    Item item;
    @OneToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    User user;
    @Column(name = "created")
    LocalDateTime created;


    public Comment(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
