package ua.malysh.domain.user;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Role {
    public static final Role USER = new Role("user");
    public static final Role ADMIN = new Role("admin");

    public Role(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
