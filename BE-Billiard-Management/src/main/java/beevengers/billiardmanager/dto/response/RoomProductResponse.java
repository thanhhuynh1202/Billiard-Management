package beevengers.billiardmanager.dto.response;


import lombok.Data;

@Data
public class RoomProductResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String productImageUrl;
}
