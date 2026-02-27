package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.room.Area;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AreaService {

    Page<Area> findAll(Pageable pageable);

    Area findById(Long id) throws ResourceNotFoundException;

    Area save(Area area) throws ResourceHasAvailable;

    Area update(Long id, Area area) throws ResourceNotFoundException, ResourceHasAvailable;

    void delete(Long id) throws ResourceNotFoundException, ResourceHasAvailable;
}