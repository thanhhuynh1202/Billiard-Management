package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.RoomRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.RoomResponse;
import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PrefixConfig.ROOM)
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<RoomResponse>> findAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "100") int size, @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<RoomResponse> rooms = roomService.findAll(pageable).map((room) -> modelMapper.map(room, RoomResponse.class));
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/findbyarea")
    public ResponseEntity<Page<RoomResponse>> findAllByAreaId(@RequestParam(value = "areaId") Long areaId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<RoomResponse> rooms = roomService.findAllByAreaId(areaId, pageable).map((room) -> modelMapper.map(room, RoomResponse.class));
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<RoomResponse> findById(@RequestParam(value = "roomId") Long roomId) throws ResourceNotFoundException {
        RoomResponse room = modelMapper.map(roomService.findById(roomId), RoomResponse.class);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/manager")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest roomRequest) throws ResourceHasAvailable {
        Room room = modelMapper.map(roomRequest, Room.class);
        room = roomService.save(room);
        RoomResponse roomResponse = modelMapper.map(room, RoomResponse.class);
        return ResponseEntity.ok(roomResponse);
    }

    @PutMapping("/manager/{roomId}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long roomId, @RequestBody RoomRequest roomRequest) throws ResourceNotFoundException {
        Room room = modelMapper.map(roomRequest, Room.class);
        room = roomService.update(roomId, room);
        RoomResponse roomResponse = modelMapper.map(room, RoomResponse.class);
        return ResponseEntity.ok(roomResponse);
    }

    @DeleteMapping("/manager/{roomId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long roomId) throws ResourceNotFoundException {
        roomService.delete(roomId);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công"));
    }
}