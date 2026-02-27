package beevengers.billiardmanager.dto.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RegisterRequest {
    String username;
    String password;
    String passwordConfirm;
    String email;
    String fullName;
    String phone;
}
