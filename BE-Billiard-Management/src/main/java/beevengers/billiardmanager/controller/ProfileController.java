package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.response.UserResponse;
import beevengers.billiardmanager.entity.User;
import beevengers.billiardmanager.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PrefixConfig.PROFILE)
@RequiredArgsConstructor
public class ProfileController {
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

//    @PostMapping("/change-password")
//    public ResponseEntity<?> changePassword() {
//        return ResponseEntity.ok().build();
//    }
}
