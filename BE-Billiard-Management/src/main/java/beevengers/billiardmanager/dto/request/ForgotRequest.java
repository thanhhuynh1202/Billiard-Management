package beevengers.billiardmanager.dto.request;

import lombok.Data;

@Data
public class ForgotRequest {
    private String username;
    private String email;
}
