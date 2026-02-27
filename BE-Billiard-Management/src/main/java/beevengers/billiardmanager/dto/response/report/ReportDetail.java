package beevengers.billiardmanager.dto.response.report;

import beevengers.billiardmanager.dto.response.InvoiceDetailResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReportDetail {
    private BigDecimal totalPrice;
    private double totalService;
    private BigDecimal totalServicePrice;
    private double totalProduct;
    private BigDecimal totalProductPrice;
    private List<InvoiceDetailResponse> invoiceDetailResponses;
}
