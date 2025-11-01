package github.gunkim.coupon.domain.model.issuance;

import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.OrderId;
import github.gunkim.coupon.domain.model.external.UserId;

import java.time.LocalDateTime;

public record IssuedCoupon(
        IssuedCouponId id,
        CouponId couponId,
        UserId userId,
        OrderId orderId,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        LocalDateTime usedAt,
        IssuedCouponStatus status
) {
}
