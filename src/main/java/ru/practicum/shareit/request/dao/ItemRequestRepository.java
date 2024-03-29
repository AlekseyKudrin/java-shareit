package ru.practicum.shareit.request.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;


public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByRequestorId(long userId);

    @Query("select itemRequest from ItemRequest itemRequest " +
            "where itemRequest.requestor.id != ?1")
    Page<ItemRequest> findAllPageable(long userId, Pageable pageable);
}
