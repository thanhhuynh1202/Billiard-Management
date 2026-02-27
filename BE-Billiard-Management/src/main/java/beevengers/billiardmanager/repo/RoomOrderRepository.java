package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.entity.room.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomOrderRepository extends JpaRepository<RoomOrder, Long> {
    List<RoomOrder> findByRoomId(Long roomId);

    Optional<RoomOrder> findByRoomAndProduct(Room room, Product product);

    @Query("SELECT CASE WHEN COUNT(ro) > 0 THEN true ELSE false END FROM RoomOrder ro WHERE ro.room.id = ?1 AND ro.product.id = ?2")
    boolean existsByRoomAndProduct(Long roomId, Long productId);

    void deleteAllByRoomId(Long id);
}