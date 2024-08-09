package authService.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import authService.entities.UserInfo;
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {
    private String userName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
