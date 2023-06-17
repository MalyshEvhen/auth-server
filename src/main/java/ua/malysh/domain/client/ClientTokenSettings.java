package ua.malysh.domain.client;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Table(name = "token_settings")
@AllArgsConstructor
@NoArgsConstructor
public class ClientTokenSettings {

    @Column(name = "access_token_ttl")
    private Long accessTokenTTL;

    @Column(name = "type")
    private String type;
}
