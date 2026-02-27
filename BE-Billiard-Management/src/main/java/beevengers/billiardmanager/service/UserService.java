package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.User;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadUserById(Long id);

    Page<User> findAll(Pageable pageable);

    User findById(Long id) throws ResourceNotFoundException;

    User save(User user) throws ResourceHasAvailable, ResourceNotFoundException;

    User update(String username, User user) throws ResourceNotFoundException, ResourceHasAvailable;

    void delete(String username) throws ResourceNotFoundException;

    String resetPassword(String username, String email) throws ResourceNotFoundException;
}
