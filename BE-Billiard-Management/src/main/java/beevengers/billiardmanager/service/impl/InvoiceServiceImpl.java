package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.invoice.Invoice;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.InvoiceRepository;
import beevengers.billiardmanager.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> findAll(Date from, Date to) {
        return invoiceRepository.findAllByCreatedAtBetween(from, to);
    }

    @Override
    public Invoice findById(Long id) throws ResourceNotFoundException {
        return invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice update(Long id, Invoice invoiceRequest) throws ResourceNotFoundException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        invoiceRequest.setId(invoice.getId());
        return invoiceRepository.save(invoiceRequest);
    }

    @Override
    public void setCanceled(Long id) throws ResourceNotFoundException {
        Invoice invoice = invoiceRepository.findByIdAndIsCanceledFalse(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        invoice.setIsCanceled(true);
        invoiceRepository.save(invoice);
    }
}
