package github.gunkim.coupon.domain.model.external;

import java.util.UUID;

public record UserId(UUID value) {
    public static UserId from(UUID uuid) {
        return new UserId(uuid);
    }
}
