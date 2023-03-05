package ru.practicum.shareit.comment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(
            name = "ITEM_ID",
            referencedColumnName = "ID"
    )
    private Item item;
    @OneToOne
    @JoinColumn(
            name = "AUTHOR_ID",
            referencedColumnName = "id"
    )
    private User author;


    public Comment(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
