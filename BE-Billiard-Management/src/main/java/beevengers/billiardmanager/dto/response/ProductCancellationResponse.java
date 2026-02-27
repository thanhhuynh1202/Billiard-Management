package beevengers.billiardmanager.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductCancellationResponse {
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long id;
    private String productName;
    private String reason;
    private BigDecimal price;
    private Double quantity;
}
