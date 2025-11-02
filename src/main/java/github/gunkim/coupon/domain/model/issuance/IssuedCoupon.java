package github.gunkim.coupon.domain.model.issuance;

import github.gunkim.coupon.domain.model.coupon.Coupon;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.OrderId;
import github.gunkim.coupon.domain.model.external.UserId;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
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
    private static final IssuedCouponStatus DEFAULT_STATUS = IssuedCouponStatus.ISSUED;

    public static IssuedCoupon issued(Coupon coupon, UserId userId) {
        return IssuedCoupon.builder()
                .id(IssuedCouponId.create())
                .couponId(coupon.id())
                .userId(userId)
                .issuedAt(LocalDateTime.now())
                .expiredAt(coupon.endDate())
                .status(DEFAULT_STATUS)
                .build();
    }
}
