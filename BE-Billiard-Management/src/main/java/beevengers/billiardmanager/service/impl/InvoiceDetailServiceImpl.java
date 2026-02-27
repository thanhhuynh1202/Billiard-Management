package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.invoice.InvoiceDetail;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.InvoiceDetailRepository;
import beevengers.billiardmanager.service.InvoiceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
    private final InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public List<InvoiceDetail> findAllByDate(Date from, Date to) {
        return invoiceDetailRepository.findAllByDate(from, to);
    }

    @Override
    public List<InvoiceDetail> findAllByDateAndArea(Date from, Date to, Long areaId) {
        return invoiceDetailRepository.findAllByDateAndArea(from, to, areaId);
    }

    @Override
    public InvoiceDetail findById(Long id) throws ResourceNotFoundException {
        return invoiceDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public InvoiceDetail save(InvoiceDetail invoiceDetail) {
        return invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public InvoiceDetail update(Long id, InvoiceDetail orderRequest) throws ResourceNotFoundException {
        invoiceDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        orderRequest.setId(id);
        return invoiceDetailRepository.save(orderRequest);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        invoiceDetailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        invoiceDetailRepository.deleteById(id);
    }
}
