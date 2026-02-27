package beevengers.billiardmanager.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InvoiceResponse {
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String roomName;
    private String customerName;
    private boolean isCanceled;
    private List<InvoiceDetailResponse> invoiceDetails = new ArrayList<>();
}
