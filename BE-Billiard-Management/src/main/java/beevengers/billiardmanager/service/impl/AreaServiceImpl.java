package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.room.Area;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.AreaRepository;
import beevengers.billiardmanager.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areasRepository;

    @Override
    public Page<Area> findAll(Pageable pageable) {
        return areasRepository.findAll(pageable);
    }

    @Override
    public Area findById(Long id) throws ResourceNotFoundException {
        return areasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public Area save(Area area) throws ResourceHasAvailable {
        if (areasRepository.existsByName(area.getName())) {
            throw new ResourceHasAvailable("name", area);
        }
        return areasRepository.save(area);
    }

    @Override
    public Area update(Long id, Area areaRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        areasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        areaRequest.setId(id);
        if (areasRepository.existsByName(areaRequest.getName())) {
            throw new ResourceHasAvailable("name", areaRequest.getName());
        }
        return areasRepository.save(areaRequest);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException, ResourceHasAvailable {
        Area area = areasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        if (areasRepository.isAreaHasUsingInRoom(id)) {
            throw new ResourceHasAvailable("id", id);
        }
        areasRepository.delete(area);
    }
}