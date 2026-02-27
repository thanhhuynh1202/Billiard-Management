package beevengers.billiardmanager.dto.response;

import beevengers.billiardmanager.dto.RoleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private String username;
    private String fullname;
    private String email;
    private List<RoleDto> roles = new ArrayList<>();
}
