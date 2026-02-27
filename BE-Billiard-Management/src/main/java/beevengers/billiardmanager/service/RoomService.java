package beevengers.billiardmanager.service;


import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {

    Page<Room> findAll(Pageable pageable);

    Page<Room> findAllByAreaId(Long areaId, Pageable pageable);

    Room findById(Long id) throws ResourceNotFoundException;

    Room save(Room room) throws ResourceHasAvailable;

    Room update(Long id, Room room) throws ResourceNotFoundException;


    void delete(Long id) throws ResourceNotFoundException;
}
