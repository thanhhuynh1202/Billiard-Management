package beevengers.billiardmanager.dto.request;

import lombok.Data;

@Data
public class RoomOrderRequest {
    private Long id;
    private String orderTimeStart;
    private double quantity;
    private Long productId;
    private Long roomId;
}
