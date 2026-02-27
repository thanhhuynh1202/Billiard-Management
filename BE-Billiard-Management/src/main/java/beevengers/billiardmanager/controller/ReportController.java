package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.response.InvoiceDetailResponse;
import beevengers.billiardmanager.dto.response.report.ReportDetail;
import beevengers.billiardmanager.entity.invoice.InvoiceDetail;
import beevengers.billiardmanager.service.InvoiceDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PrefixConfig.REPORT)
@RequiredArgsConstructor
public class ReportController {
    private final InvoiceDetailService InvoiceDetailService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ReportDetail> findAll(@RequestParam(value = "from") String from, @RequestParam(value = "to") String to, @RequestParam(value = "area", required = false) Long area) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = formatter.parse(from);
        Date toDate = formatter.parse(to);
        List<InvoiceDetail> invoiceDetails = (area != null) ? InvoiceDetailService.findAllByDateAndArea(fromDate, toDate, area) : InvoiceDetailService.findAllByDate(fromDate, toDate);
        List<InvoiceDetailResponse> invoiceDetailResponses = invoiceDetails.stream().map(invoiceDetail -> modelMapper.map(invoiceDetail, InvoiceDetailResponse.class)).toList();

        // gộp các sản phẩm giống nhau
        invoiceDetailResponses = invoiceDetailResponses.stream().collect(Collectors.groupingBy((e) -> e.getProductName() + e.getPrice())).values().stream().map((e) -> {
            InvoiceDetailResponse invoiceDetailResponse = e.get(0);
            invoiceDetailResponse.setQuantity(e.stream().mapToDouble(InvoiceDetailResponse::getQuantity).sum());
            return invoiceDetailResponse;
        }).toList();

        ReportDetail reportDetail = new ReportDetail();
        reportDetail.setInvoiceDetailResponses(invoiceDetailResponses);
        reportDetail.setTotalPrice(invoiceDetailResponses.stream().map((e) -> {
            BigDecimal price = e.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(e.getQuantity());
            return price.multiply(quantity);
        }).reduce(BigDecimal.ZERO, BigDecimal::add));
        reportDetail.setTotalService(invoiceDetailResponses.stream().filter(InvoiceDetailResponse::isProductHourly).mapToDouble(InvoiceDetailResponse::getQuantity).sum());
        reportDetail.setTotalServicePrice(invoiceDetailResponses.stream().filter(InvoiceDetailResponse::isProductHourly).map((e) -> {
            BigDecimal price = e.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(e.getQuantity());
            return price.multiply(quantity);
        }).reduce(BigDecimal.ZERO, BigDecimal::add));
        reportDetail.setTotalProduct(invoiceDetailResponses.stream().filter((e) -> !e.isProductHourly()).mapToDouble(InvoiceDetailResponse::getQuantity).sum());
        reportDetail.setTotalProductPrice(invoiceDetailResponses.stream().filter((e) -> !e.isProductHourly()).map((e) -> {
            BigDecimal price = e.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(e.getQuantity());
            return price.multiply(quantity);
        }).reduce(BigDecimal.ZERO, BigDecimal::add));
        return ResponseEntity.ok(reportDetail);
    }
}
