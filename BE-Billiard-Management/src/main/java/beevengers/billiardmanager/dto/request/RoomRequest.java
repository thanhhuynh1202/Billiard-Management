package beevengers.billiardmanager.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomRequest {
    private Long id;
    private String name;
    private Boolean active;
    private Long areaId;
    private List<RoomProductRequest> roomProducts = new ArrayList<>();
}
