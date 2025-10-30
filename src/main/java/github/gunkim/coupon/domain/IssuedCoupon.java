package github.gunkim.coupon.domain;

import java.time.LocalDateTime;

public record IssuedCoupon(
        Long id,
        Long couponId,
        Long userId,
        Long orderId,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        LocalDateTime usedAt,
        IssuedCouponStatus status
) {
}
