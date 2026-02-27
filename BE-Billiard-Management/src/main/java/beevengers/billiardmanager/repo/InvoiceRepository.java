package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByCreatedAtBetween(Date from, Date to);

    Optional<Invoice> findByIdAndIsCanceledFalse(Long id);
}