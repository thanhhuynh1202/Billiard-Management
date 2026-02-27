package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.invoice.InvoiceDetail;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

public interface InvoiceDetailService {

    List<InvoiceDetail> findAllByDate(Date from, Date to);

    List<InvoiceDetail> findAllByDateAndArea(Date from, Date to, Long areaId);

    InvoiceDetail findById(Long id) throws ResourceNotFoundException;

    InvoiceDetail save(InvoiceDetail invoiceDetail);

    InvoiceDetail update(Long id, InvoiceDetail invoiceDetail) throws ResourceNotFoundException;

    void delete(Long orderDetailId) throws ResourceNotFoundException;
}
