package github.gunkim.coupon.domain.model.issuance;

import java.util.UUID;

public record IssuedCouponId(UUID value) {
    public static IssuedCouponId create() {
        return new IssuedCouponId(UUID.randomUUID());
    }
}
