package ru.practicum.shareit.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
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
