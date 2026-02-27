package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.invoice.Invoice;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

public interface InvoiceService {
    List<Invoice> findAll();

    List<Invoice> findAll(Date from, Date to);

    Invoice findById(Long id) throws ResourceNotFoundException;

    Invoice save(Invoice invoice);

    Invoice update(Long id, Invoice invoice) throws ResourceNotFoundException;

    void setCanceled(Long id) throws ResourceNotFoundException;
}
