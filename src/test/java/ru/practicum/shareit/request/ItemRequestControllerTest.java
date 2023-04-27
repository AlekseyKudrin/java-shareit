package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ShareItTests.HEADER;

@WebMvcTest(ItemRequestController.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestControllerTest {

    @Autowired
    private final ObjectMapper objectMapper;
    User user = new User(
            1L,
            "name",
            "email@email.ru",
            null);
    ItemRequestDto itemRequestDto = new ItemRequestDto(
            1L,
            1L,
            "description",
            LocalDateTime.now(),
            new ArrayList<>());
    @MockBean
    private ItemRequestService itemRequestService;
    @Autowired
    private MockMvc mvc;

    @Test
    void create() throws Exception {
        when(itemRequestService.create(anyLong(), any())).thenReturn(itemRequestDto);

        mvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER, 1L)
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.requestorid").value(1L))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    void getRequestsOwner() throws Exception {
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user);
        ItemRequestDto req = ItemRequestMapper.toItemRequestDto(itemRequest);
        when(itemRequestService.getRequestsOwner(anyLong())).thenReturn(Collections.singletonList(req));

        mvc.perform(get("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].requestorid").value(1L))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void getAll() throws Exception {
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user);
        ItemRequestDto req = ItemRequestMapper.toItemRequestDto(itemRequest);
        when(itemRequestService.getAll(anyLong(), anyInt(), anyInt())).
                thenReturn(Collections.singletonList(req));

        mvc.perform(get("/requests/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].requestorid").value(1L))
                .andExpect(jsonPath("$[0].description").value("description"));
    }

    @Test
    void getById() throws Exception {
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user);
        ItemRequestDto req = ItemRequestMapper.toItemRequestDto(itemRequest);
        when(itemRequestService.getById(anyLong(), anyLong())).thenReturn(req);

        mvc.perform(get("/requests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.requestorid").value(1L))
                .andExpect(jsonPath("$.description").value("description"));
    }
}