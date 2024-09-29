package poposeop.account.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import poposeop.account.dto.LoginRequest;
import poposeop.account.dto.RegisterRequest;
import poposeop.account.entity.InGameEntity;
import poposeop.account.service.AccountService;
import poposeop.account.utils.ApiResponse;
import poposeop.account.utils.JwtUtil;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    public AccountController(AccountService accountService,JwtUtil jwtUtil) {
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> orderRegister(@RequestBody RegisterRequest request){
        try{
            accountService.register(request);
            return ResponseEntity.ok(new ApiResponse(true,"-"));
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false,new ApiResponse(false,e.getMessage())));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,new ApiResponse(false,e.getMessage())));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> orderLogin(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Boolean result = accountService.login(request);
            if (result) {
                String accessToken = jwtUtil.generateAccessToken(request.getUsername());
                String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());

                ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(60 * 15) // 15분
                        .sameSite("Strict")
                        .build();

                ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(60 * 60 * 24 * 7) // 7일
                        .sameSite("Strict")
                        .build();

                response.addHeader("Set-Cookie", accessTokenCookie.toString());
                response.addHeader("Set-Cookie", refreshTokenCookie.toString());

                return ResponseEntity.ok(new ApiResponse(true, ""));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid nickname or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, new ApiResponse(false, e.getMessage())));
        }
    }

//    @GetMapping("/check-account")
//    public ResponseEntity<ApiResponse> orderCheckAccount(){
//
//    }
}
