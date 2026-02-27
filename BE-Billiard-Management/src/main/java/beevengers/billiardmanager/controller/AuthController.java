package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.ForgotRequest;
import beevengers.billiardmanager.dto.request.LoginRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.BearerTokenResponse;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.security.CustomUserDetails;
import beevengers.billiardmanager.security.jwt.JwtTokenProvider;
import beevengers.billiardmanager.service.MailService;
import beevengers.billiardmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PrefixConfig.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API is working :3");
    }

    @PostMapping("/login")
    public ResponseEntity<BearerTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(userDetails);
        return ResponseEntity.ok(new BearerTokenResponse(jwt, "Bearer"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ForgotRequest forgotRequest) throws ResourceNotFoundException {
        String newPass = userService.resetPassword(forgotRequest.getUsername(), forgotRequest.getEmail());
        mailService.sendMail(forgotRequest.getEmail(), "Reset password", "Mật khẩu mới của bạn là: " + newPass);
        return ResponseEntity.ok(new ApiResponse(true, "Mật khẩu mới đã được gửi đến email của bạn"));
    }
}