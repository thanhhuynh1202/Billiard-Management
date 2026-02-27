package beevengers.billiardmanager.dto.request;

import lombok.Data;

@Data
public class RoomProductRequest {
    private Long id;
    private Long roomId;
    private Long productId;
}
