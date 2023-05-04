package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ShareItTests.HEADER;

@AutoConfigureMockMvc
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private UserDto user1Dto;

    private ItemDto item1Dto;

    ItemControllerTest() {
    }

    @BeforeEach
    void beforeEach() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User(1L, "User2 name", "user@mail.com", new HashSet<>());
        user1Dto = UserMapper.toUserDto(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Item1 name");
        item.setDescription("Item1 description");
        item.setAvailable(true);
        item.setOwner(1L);
        item.setRequestId(1L);
        item1Dto = ItemMapper.toItemDto(item);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Comment1 text");
        comment.setItem(item);
        comment.setUser(user);
        comment.setCreated(now);
    }

    @Test
    void create() throws Exception {
        when(itemService.create(anyLong(), any(ItemDto.class)))
                .thenReturn(item1Dto);

        mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString(item1Dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER, user1Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(item1Dto)));

        item1Dto.setName("");
        mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString(item1Dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER, user1Dto.getId()))
                .andExpect(status().isBadRequest());

    }

    @Test
    void update() throws Exception {
        when((itemService.update(anyLong(), anyLong(), any(ItemDto.class))))
                .thenReturn(item1Dto);

        mockMvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(item1Dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER, user1Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(item1Dto)));
    }

    @Test
    void getTest() throws Exception {
        when(itemService.get(anyLong(), anyLong()))
                .thenReturn(item1Dto);

        mockMvc.perform(get("/items/1")
                        .header(HEADER, user1Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(item1Dto)));
    }

    @Test
    void getAll() throws Exception {
        when(itemService.getAll(anyLong()))
                .thenReturn(List.of(item1Dto));

        mockMvc.perform(get("/items")
                        .header(HEADER, user1Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(item1Dto))));
    }

    @Test
    void search() throws Exception {
        when(itemService.search(anyString()))
                .thenReturn(List.of(item1Dto));

        mockMvc.perform(get("/items/search")
                        .param("text", "Item1")
                        .header(HEADER, user1Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(item1Dto))));
    }
}