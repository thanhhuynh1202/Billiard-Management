package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.product.ProductCancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCancellationRepository extends JpaRepository<ProductCancellation, Long> {
}