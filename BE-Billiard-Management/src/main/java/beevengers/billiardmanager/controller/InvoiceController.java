package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.InvoiceDetailRequest;
import beevengers.billiardmanager.dto.request.ProductCancellationRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.InvoiceDetailResponse;
import beevengers.billiardmanager.dto.response.InvoiceResponse;
import beevengers.billiardmanager.entity.invoice.Invoice;
import beevengers.billiardmanager.entity.invoice.InvoiceDetail;
import beevengers.billiardmanager.entity.product.ProductCancellation;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.InvoiceDetailService;
import beevengers.billiardmanager.service.InvoiceService;
import beevengers.billiardmanager.service.ProductCancellationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PrefixConfig.INVOICE)
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceDetailService invoiceDetailService;
    private final ProductCancellationService productCancellationService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> findAll(@RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) throws ParseException {
        if (from != null && to != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fromDate = formatter.parse(from);
            Date toDate = formatter.parse(to);
            List<InvoiceResponse> orders = invoiceService.findAll(fromDate, toDate).stream().map((element) -> modelMapper.map(element, InvoiceResponse.class)).collect(Collectors.toList());
            return ResponseEntity.ok(orders);
        }
        List<InvoiceResponse> orders = invoiceService.findAll().stream().map((element) -> modelMapper.map(element, InvoiceResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<InvoiceResponse> findById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Invoice invoice = invoiceService.findById(id);
        InvoiceResponse invoiceResponse = modelMapper.map(invoice, InvoiceResponse.class);
        return ResponseEntity.ok(invoiceResponse);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<InvoiceResponse> update(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Invoice invoice = invoiceService.findById(id);
        invoice.setIsCanceled(true);
        invoice = invoiceService.update(id, invoice);
        InvoiceResponse invoiceResponse = modelMapper.map(invoice, InvoiceResponse.class);
        return ResponseEntity.ok(invoiceResponse);
    }

    @PutMapping("/detail/{id}")
    public ResponseEntity<InvoiceDetailResponse> update(@PathVariable("id") Long id, @RequestBody InvoiceDetailRequest invoiceDetailRequest) throws ResourceNotFoundException {
        InvoiceDetail invoiceDetail = modelMapper.map(invoiceDetailRequest, InvoiceDetail.class);
        invoiceDetail = invoiceDetailService.update(id, invoiceDetail);
        InvoiceDetailResponse invoiceDetailResponse = modelMapper.map(invoiceDetail, InvoiceDetailResponse.class);
        return ResponseEntity.ok(invoiceDetailResponse);
    }

    @DeleteMapping("/detail")
    public ResponseEntity<ApiResponse> delete(@RequestBody ProductCancellationRequest productCancellationRequest) throws ResourceNotFoundException {
        ProductCancellation productCancellation = modelMapper.map(productCancellationRequest, ProductCancellation.class);

        InvoiceDetail invoiceDetail = invoiceDetailService.findById(productCancellation.getId());
        if (invoiceDetail.getQuantity() > productCancellation.getQuantity()) {
            invoiceDetail.setQuantity(invoiceDetail.getQuantity() - productCancellation.getQuantity());
            invoiceDetailService.update(invoiceDetail.getId(), invoiceDetail);
        } else {
            productCancellation.setQuantity(invoiceDetail.getQuantity());
            invoiceDetailService.delete(invoiceDetail.getId());
        }

        productCancellationService.save(productCancellation);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công!"));
    }
}
