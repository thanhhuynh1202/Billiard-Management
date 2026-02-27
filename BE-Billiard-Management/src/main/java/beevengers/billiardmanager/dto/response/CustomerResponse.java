package beevengers.billiardmanager.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private BigDecimal balance;

}
