package poposeop.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "username", nullable = false, length = 16)
    String username;

    @Column(name="password",nullable = false, length = 16)
    String password;
}
