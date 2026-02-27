package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.AreaRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.AreaResponse;
import beevengers.billiardmanager.entity.room.Area;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PrefixConfig.AREA)
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<AreaResponse>> findAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<AreaResponse> areaResponses = areaService.findAll(pageable).map(area -> modelMapper.map(area, AreaResponse.class));
        return ResponseEntity.ok(areaResponses);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<AreaResponse> findById(@RequestParam("areaId") Long id) throws ResourceNotFoundException {
        AreaResponse area = modelMapper.map(areaService.findById(id), AreaResponse.class);
        return ResponseEntity.ok(area);
    }

    @PostMapping("/manager")
    public ResponseEntity<AreaResponse> create(@RequestBody AreaRequest areaRequest) throws ResourceHasAvailable {
        Area area = modelMapper.map(areaRequest, Area.class);
        area = areaService.save(area);
        AreaResponse areaResponse = modelMapper.map(area, AreaResponse.class);
        return ResponseEntity.ok(areaResponse);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<AreaResponse> update(@PathVariable("id") Long id, @RequestBody AreaRequest areaRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        Area area = modelMapper.map(areaRequest, Area.class);
        area = areaService.update(id, area);
        AreaResponse areaResponse = modelMapper.map(area, AreaResponse.class);
        return ResponseEntity.ok(areaResponse);
    }

    @DeleteMapping("/manager/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) throws ResourceNotFoundException, ResourceHasAvailable {
        areaService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công"));
    }
}