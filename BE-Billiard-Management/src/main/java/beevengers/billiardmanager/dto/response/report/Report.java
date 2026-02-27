package beevengers.billiardmanager.dto.response.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Report {
    private BigDecimal totalPrice;
    private double totalService;
    private double totalProduct;
    private List<ReportDetail> areaReports;
}
