package authService.controller;

import authService.entities.RefreshTokens;
import authService.model.UserInfoDTO;
import authService.response.JwtResponseDTO;
import authService.service.JwtService;
import authService.service.RefreshTokenService;
import authService.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isSignUped = userDetailService.signupUser(userInfoDTO);
            if (Boolean.FALSE.equals(isSignUped)) {
                return new ResponseEntity<>("Alreaady Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshTokens refreshTokens = refreshTokenService.createRefreshToken(userInfoDTO.getUserName());
            String jwtToken = jwtService.GenerateToken(userInfoDTO.getUserName());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken)
                    .token(refreshTokens.getToken()).build(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception iin User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
