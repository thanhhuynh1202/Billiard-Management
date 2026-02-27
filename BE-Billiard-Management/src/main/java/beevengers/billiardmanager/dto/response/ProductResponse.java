package beevengers.billiardmanager.dto.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String imageId;
    private String imageUrl;
    private String price;
    private String type;
    private String unit;
    private String categoryId;
    private String categoryName;
    private boolean hourly;
    private boolean active;
}
