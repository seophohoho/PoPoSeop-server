package poposeop.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="ingame")
public class InGameEntity {
    @Id
    @Column(name = "nickname", nullable = false, length = 12)
    private String nickname;

    @Column(name = "username", nullable = false, length = 16)
    private String username;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "cdatetime", nullable = false)
    private LocalDateTime cdatetime;

    @Column(name = "udatetime", nullable = false)
    private LocalDateTime udatetime;

    @Column(name = "last_region", nullable = false, length = 3)
    private String lastRegion;

    @Column(name = "last_pos_x", nullable = false)
    private Integer lastPosX;

    @Column(name = "last_pos_y", nullable = false)
    private Integer lastPosY;

    @Column(name = "last_pokemon", nullable = false)
    private String lastPokemon;

    @Column(name = "playtime", nullable = false)
    private LocalDateTime playtime;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private AccountEntity accountEntity;

}
