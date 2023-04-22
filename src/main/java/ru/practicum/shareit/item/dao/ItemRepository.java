package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findItemsByOwner(Long ownerId);

    Optional<Item> findItemById(Long id);

    Optional<Item> findItemByIdAndOwner(Long id, Long ownerId);

    @Query(value = "SELECT * FROM ITEMS i " +
            "WHERE i.IS_AVAILABLE = true AND (LOWER(i.NAME) LIKE LOWER(CONCAT('%', ?1,'%')) " +
            "OR LOWER(i.DESCRIPTION) LIKE LOWER(CONCAT('%', ?1,'%')))", nativeQuery = true)
    List<Item> searchItems(String text);

    List<Item> findItemByRequestId(long requestId);

    List<Item> searchItemByRequestId (@Param("ids") List<Long> ids);
}
