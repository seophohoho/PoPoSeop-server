package poposeop.account.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import poposeop.account.dto.LoginRequest;
import poposeop.account.dto.RegisterRequest;
import poposeop.account.entity.AccountEntity;
import poposeop.account.entity.InGameEntity;
import poposeop.account.repository.AccountRepository;
import poposeop.account.repository.InGameRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final InGameRepository inGameRepository;

    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,InGameRepository inGameRepository,PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.inGameRepository = inGameRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        if(accountRepository.existsByUsername(request.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        AccountEntity entity = new AccountEntity();
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        System.out.println(entity.getPassword().length());

        accountRepository.save(entity);
    }

    public Boolean login(LoginRequest request){
        return accountRepository.findById(request.getUsername())
                .map(accountEntity -> {
                    boolean check = passwordEncoder.matches(request.getPassword(),accountEntity.getPassword());
                    return check;
                })
                .orElse(false);
    }

    public InGameEntity getAccountByUsername(String username) {
        return inGameRepository.findByUsername(username);
    }
}
