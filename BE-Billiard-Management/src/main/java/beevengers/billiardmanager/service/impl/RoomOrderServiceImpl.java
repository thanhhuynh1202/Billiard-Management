package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.entity.room.RoomOrder;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.RoomOrderRepository;
import beevengers.billiardmanager.service.RoomOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomOrderServiceImpl implements RoomOrderService {

    private final RoomOrderRepository roomOrderRepository;

    @Override
    public List<RoomOrder> findAllByRoomId(Long roomId) {
        return roomOrderRepository.findByRoomId(roomId);
    }

    @Override
    public RoomOrder findById(Long id) throws ResourceNotFoundException {
        return roomOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public RoomOrder findByRoomAndProduct(Room room, Product product) {
        return roomOrderRepository.findByRoomAndProduct(room, product).orElse(null);
    }

    @Override
    public boolean existsByRoomAndProduct(Room room, Product product) {
        return roomOrderRepository.existsByRoomAndProduct(room.getId(), product.getId());
    }

    @Override
    public RoomOrder save(RoomOrder roomOrder) {
        return roomOrderRepository.save(roomOrder);
    }

    @Override
    public RoomOrder update(Long id, RoomOrder roomOrderRequest) throws ResourceNotFoundException {
        roomOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        roomOrderRequest.setId(id);

        return roomOrderRepository.save(roomOrderRequest);

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        roomOrderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        roomOrderRepository.deleteById(id);
    }
}
