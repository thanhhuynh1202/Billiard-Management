package beevengers.billiardmanager.service.impl;


import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.RoomRepository;
import beevengers.billiardmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    public final RoomRepository roomRepository;


    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Page<Room> findAllByAreaId(Long areaId, Pageable pageable) {
        return roomRepository.findAllByAreaId(areaId, pageable);
    }

    @Override
    public Room findById(Long id) throws ResourceNotFoundException {
        return roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));

    }

    @Override
    public Room save(Room room) throws ResourceHasAvailable {
        Room foundRoom = roomRepository.findByName(room.getName());
        if (foundRoom == null && roomRepository.existsByName(room.getName())) {
            throw new ResourceHasAvailable("name", room.getName());
        }
        return roomRepository.save(room);
    }

    @Override
    public Room update(Long id, Room roomRequest) throws ResourceNotFoundException {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        roomRequest.setId(id);
        return roomRepository.save(roomRequest);


    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        roomRepository.deleteById(id);
    }
}
