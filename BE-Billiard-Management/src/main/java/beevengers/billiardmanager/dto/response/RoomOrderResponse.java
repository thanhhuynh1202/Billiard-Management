package beevengers.billiardmanager.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RoomOrderResponse implements Serializable {
    private Long id;
    private Long roomId;
    private String createdAt;
    private String updatedAt;
    private String orderTimeStart;
    private double quantity;
    private Long productId;
    private String productName;
    private String productUnit;
    private BigDecimal productPrice;
    private Boolean productHourly;
    private String productUnitName;
}