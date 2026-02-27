package beevengers.billiardmanager.dto.request;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private Long imageId;
    private String imageUrl;
    private String price;
    private String type;
    private String unit;
    private String categoryId;
    private boolean hourly;
    private boolean active;
}
