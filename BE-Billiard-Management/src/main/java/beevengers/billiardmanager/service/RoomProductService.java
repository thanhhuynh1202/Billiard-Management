package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.room.RoomProduct;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;

import java.util.List;

public interface RoomProductService {
    List<RoomProduct> findAll();

    List<RoomProduct> findAllByRoomId(Long roomId);

    RoomProduct save(RoomProduct roomProduct);

    RoomProduct update(long id, RoomProduct roomProduct) throws ResourceNotFoundException;

    void delete(long id);

    void deleteAllByRoomId(Long roomId);
}
