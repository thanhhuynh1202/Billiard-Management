package beevengers.billiardmanager.dto.request;

import beevengers.billiardmanager.dto.RoleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequest {
    private Long id;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private List<RoleDto> roles = new ArrayList<>();
}
