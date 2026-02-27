package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.UserRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.UserResponse;
import beevengers.billiardmanager.entity.User;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.RoleRepository;
import beevengers.billiardmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(PrefixConfig.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserResponse> users = userService.findAll(pageable).map(user -> modelMapper.map(user, UserResponse.class));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<UserResponse> findById(@RequestParam(value = "userId") Long id) throws ResourceNotFoundException {
        UserResponse user = modelMapper.map(userService.findById(id), UserResponse.class);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/manager")
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest userRequest) throws ResourceHasAvailable, ResourceNotFoundException {
        User user = modelMapper.map(userRequest, User.class);
        user.getRoles().add(roleRepository.findById(3L).orElseThrow(() -> new ResourceNotFoundException("Role", 3L)));
        user = userService.save(user);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/manager/{username}")
    public ResponseEntity<UserResponse> update(@PathVariable String username, @RequestBody UserRequest userRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        User user = modelMapper.map(userRequest, User.class);
        user.getRoles().add(roleRepository.findById(3L).orElseThrow(() -> new ResourceNotFoundException("Role", 3L)));
        user = userService.update(username, user);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/manager/{username}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String username) throws ResourceNotFoundException {
        userService.delete(username);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công"));
    }
}
