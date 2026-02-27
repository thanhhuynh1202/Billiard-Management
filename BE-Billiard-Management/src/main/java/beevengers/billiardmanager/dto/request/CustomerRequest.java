package beevengers.billiardmanager.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerRequest {
    private String name;
    private String gender;
    private String email;
    private String phone;
    private BigDecimal balance;
}
