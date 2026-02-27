package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.room.RoomProduct;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.RoomProductRepository;
import beevengers.billiardmanager.service.RoomProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomProductImpl implements RoomProductService {
    private final RoomProductRepository roomProductRepository;

    @Override
    public List<RoomProduct> findAll() {
        return roomProductRepository.findAll();
    }

    @Override
    public List<RoomProduct> findAllByRoomId(Long roomId) {
        return roomProductRepository.findAllByRoomId(roomId);
    }

    @Override
    public RoomProduct save(RoomProduct roomProduct) {
        return roomProductRepository.save(roomProduct);
    }

    @Override
    public RoomProduct update(long id, RoomProduct roomProduct) throws ResourceNotFoundException {
        RoomProduct found = roomProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RoomProduct", id));
        roomProduct.setId(found.getId());
        return roomProductRepository.save(roomProduct);
    }

    @Override
    public void delete(long id) {
        roomProductRepository.deleteById(id);
    }

    @Override
    public void deleteAllByRoomId(Long roomId) {
        roomProductRepository.deleteAllByRoomId(roomId);
    }
}
