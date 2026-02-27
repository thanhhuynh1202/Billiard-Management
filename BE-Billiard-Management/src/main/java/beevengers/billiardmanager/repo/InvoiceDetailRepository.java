package beevengers.billiardmanager.repo;

import beevengers.billiardmanager.entity.invoice.InvoiceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    @Query("SELECT o FROM InvoiceDetail o WHERE o.invoice.id = ?1")
    Page<InvoiceDetail> findAllByInvoiceId(Long invoiceId, Pageable pageable);

    @Query("SELECT o FROM InvoiceDetail o WHERE o.invoice.createdAt BETWEEN ?1 AND ?2")
    List<InvoiceDetail> findAllByDate(Date from, Date to);

    @Query("SELECT o FROM InvoiceDetail o WHERE o.invoice.createdAt BETWEEN ?1 AND ?2 AND o.invoice.room.area.id = ?3")
    List<InvoiceDetail> findAllByDateAndArea(Date from, Date to, Long areaId);
}