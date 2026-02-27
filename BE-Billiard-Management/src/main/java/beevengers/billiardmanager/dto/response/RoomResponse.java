package beevengers.billiardmanager.dto.response;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponse {
    private Long id;
    private String name;
    private Long areaId;
    private String areaName;
    private Boolean active;
    private Long customerId;
    private String customerName;
    private List<RoomOrderResponse> roomOrders = new ArrayList<>();
    private List<RoomProductResponse> roomProducts = new ArrayList<>();
}
