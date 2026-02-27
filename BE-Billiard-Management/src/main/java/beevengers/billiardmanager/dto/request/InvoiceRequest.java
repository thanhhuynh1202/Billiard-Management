package beevengers.billiardmanager.dto.request;

import lombok.Data;

@Data
public class InvoiceRequest {
    private Boolean isCanceled = false;
    private Long roomId;
    private Long customerId;
}
