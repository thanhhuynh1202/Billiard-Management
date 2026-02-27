package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByAreaId(Long areaId, Pageable pageable);

    Room findByName(String name);

    Boolean existsByName(String name);
}