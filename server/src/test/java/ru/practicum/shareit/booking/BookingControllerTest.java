package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.ShareItTests.HEADER;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private UserDto user2Dto;

    private BookingDto booking1Dto;

    @BeforeEach
    void beforeEach() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.plusDays(1);
        LocalDateTime end = now.plusDays(2);

        User user = new User(2L, "User2 name", "user@mail.com", new HashSet<>());
        user2Dto = UserMapper.toUserDto(user);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item1 name");
        item1.setDescription("Item1 description");
        item1.setAvailable(true);
        item1.setOwner(1L);
        item1.setRequestId(1L);

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setStart(start);
        booking1.setEnd(end);
        booking1.setItem(item1);
        booking1.setBooker(user);
        booking1.setStatus(BookingStatus.WAITING);
        booking1Dto = BookingMapper.toBookingDto(booking1);
    }

    @Test
    void create() throws Exception {
        when(bookingService.create(anyLong(), any(BookingDto.class)))
                .thenReturn(booking1Dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .content(mapper.writeValueAsString(booking1Dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER, user2Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(booking1Dto)));

        booking1Dto.setItemId(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .content(mapper.writeValueAsString(booking1Dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HEADER, user2Dto.getId()))
                .andExpect(status().isBadRequest());

    }

    @Test
    void statusTest() throws Exception {
        when(bookingService.status(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(booking1Dto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/1")
                        .param("approved", "true")
                        .header(HEADER, user2Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(booking1Dto)));
    }

    @Test
    void getBookingById() throws Exception {
        when(bookingService.getBookingById(anyLong(), anyLong()))
                .thenReturn(booking1Dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/1")
                        .header(HEADER, user2Dto.getId()))

                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(booking1Dto)));
    }

    @Test
    void getAllBookingsUser() throws Exception {
        when(bookingService.getAllBookingsUser(anyLong(), any(String.class), anyInt(), anyInt()))
                .thenReturn(List.of(booking1Dto));

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings")
                        .header(HEADER, user2Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(booking1Dto))));
    }

    @Test
    void getAllBookingsOwner() throws Exception {
        when(bookingService.getAllBookingsOwner(anyLong(), any(String.class), anyInt(), anyInt()))
                .thenReturn(List.of(booking1Dto));

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner")
                        .header(HEADER, user2Dto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(booking1Dto))));
    }
}