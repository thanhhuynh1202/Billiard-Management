package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.entity.room.RoomOrder;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;

import java.util.List;

public interface RoomOrderService {
    List<RoomOrder> findAllByRoomId(Long roomId);

    RoomOrder findById(Long id) throws ResourceNotFoundException;

    RoomOrder findByRoomAndProduct(Room room, Product product);

    boolean existsByRoomAndProduct(Room room, Product product);

    RoomOrder save(RoomOrder roomOrder);

    RoomOrder update(Long id, RoomOrder roomOrder) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
