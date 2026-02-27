package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByName(RoleType name);
}