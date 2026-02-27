package beevengers.billiardmanager.dto.response;

import lombok.Data;

@Data
public class BearerTokenResponse {
    private String accessToken;
    private String tokenType;

    public BearerTokenResponse(String accessToken, String tokenType) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }
}
