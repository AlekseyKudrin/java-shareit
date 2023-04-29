package ru.practicum.shareit.request.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.request.dao.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemRequestServiceImplTest {

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Mock
    private ItemRequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    private final User user = new User(1L, "User1 name", "user1@mail.com", new HashSet<>());

    private ItemRequestDto itemRequestDto = new ItemRequestDto(
            1L,
            1L,
            "description",
            LocalDateTime.now(),
            null);

    private ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user);

    @Test
    void create() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        when(requestRepository.save(any()))
                .thenReturn(itemRequest);


        ItemRequestDto actual = itemRequestService.create(user.getId(), itemRequestDto);

        assertEquals(itemRequestDto.getId(), actual.getId());
        verify(requestRepository, Mockito.times(1)).save(any());
    }

    @Test
    void getRequestsOwner() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        List<ItemRequestDto> responseList = itemRequestService.getRequestsOwner(user.getId());
        assertTrue(responseList.isEmpty());
        verify(requestRepository).findAllByRequestorId(anyLong());
    }

    @Test
    void getAll() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));

        when(requestRepository.findAllPageable(anyLong(), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(itemRequest)));

        List<ItemRequestDto> all = itemRequestService.getAll(
                user.getId(),
                0,
                10);

        assertEquals(1, all.size());
        assertEquals(1, all.get(0).getId());
        assertEquals("description", all.get(0).getDescription());
        assertEquals(user.getId(), all.get(0).getRequestorid());
        assertEquals(Collections.emptyList(), all.get(0).getItems());
    }

    @Test
    void getById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        List<ItemRequestDto> responseList = itemRequestService.getRequestsOwner(user.getId());
        assertTrue(responseList.isEmpty());
        verify(requestRepository).findAllByRequestorId(anyLong());
    }
}