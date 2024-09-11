package poposeop.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import poposeop.account.dto.RegisterRequest;
import poposeop.account.entity.AccountEntity;
import poposeop.account.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository,PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
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
}
