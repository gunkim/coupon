package github.gunkim.coupon.domain.model.coupon;

import java.util.UUID;

public record CouponId(UUID value) {
    public static CouponId from(UUID uuid) {
        return new CouponId(uuid);
    }
}
