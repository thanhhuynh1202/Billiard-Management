package beevengers.billiardmanager.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCancellationRequest {
    private Long id;
    private String reason;
    private Long productId;
    private Double quantity;
    private BigDecimal price;
}
