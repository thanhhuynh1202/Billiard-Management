package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.room.RoomProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomProductRepository extends JpaRepository<RoomProduct, Long> {
    List<RoomProduct> findAllByRoomId(Long roomId);

    void deleteAllByRoomId(Long roomId);
}