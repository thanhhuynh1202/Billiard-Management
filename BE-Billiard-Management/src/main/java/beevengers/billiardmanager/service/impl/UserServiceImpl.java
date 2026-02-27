package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.User;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.UserRepository;
import beevengers.billiardmanager.security.CustomUserDetails;
import beevengers.billiardmanager.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("id"));
        return new CustomUserDetails(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public User save(User user) throws ResourceHasAvailable, ResourceNotFoundException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceHasAvailable("email", user);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceHasAvailable("username", user);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(String username, User userRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("username", username));
        if (!userRequest.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceHasAvailable("email", user);
        }
        if (!userRequest.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceHasAvailable("username", user);
        }
        userRequest.setId(user.getId());
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userRepository.save(userRequest);
    }

    @Override
    public void delete(String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("username", username));
        userRepository.delete(user);
    }

    @Override
    public String resetPassword(String username, String email) throws ResourceNotFoundException {
        if (!userRepository.existsByUsername(username) || !userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("username or email", username + ", " + email);
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("username", username));
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * charset.length());
            newPassword.append(charset.charAt(random));
        }
        user.setPassword(passwordEncoder.encode(newPassword.toString()));
        userRepository.save(user);
        return newPassword.toString();
    }
}
