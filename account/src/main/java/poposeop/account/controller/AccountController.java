package poposeop.account.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import poposeop.account.dto.LoginRequest;
import poposeop.account.dto.RegisterRequest;
import poposeop.account.service.AccountService;
import poposeop.account.utils.ApiResponse;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
    public ResponseEntity<ApiResponse> orderLogin(@RequestBody LoginRequest request) {
        try {
            Boolean result = accountService.login(request);
            if (result) {
                return ResponseEntity.ok(new ApiResponse(true, "-"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Invalid nickname or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, new ApiResponse(false, e.getMessage())));
        }
    }
}
