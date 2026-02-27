package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.room.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Room r JOIN r.area c WHERE r.area.id = ?1")
    boolean isAreaHasUsingInRoom(Long id);

    Boolean existsByName(String name);
}