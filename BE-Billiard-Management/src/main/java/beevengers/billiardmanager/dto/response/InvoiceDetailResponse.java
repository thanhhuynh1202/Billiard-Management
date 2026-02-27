package beevengers.billiardmanager.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InvoiceDetailResponse implements Serializable {
    private boolean productHourly = false;
    private String productName;
    private BigDecimal price;
    private double quantity;
}
