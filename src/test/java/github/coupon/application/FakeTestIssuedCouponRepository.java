package github.coupon.application;

import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.UserId;
import github.gunkim.coupon.domain.model.issuance.IssuedCoupon;
import github.gunkim.coupon.domain.model.issuance.IssuedCouponId;
import github.gunkim.coupon.domain.repository.IssuedCouponRepository;

import java.util.HashMap;
import java.util.Map;

public class FakeTestIssuedCouponRepository implements IssuedCouponRepository {
    private final Map<IssuedCouponId, IssuedCoupon> issuedCoupons = new HashMap<>();

    @Override
    public boolean existsByCouponIdAndUserId(CouponId couponId, UserId userId) {
        return issuedCoupons.values().stream()
                .anyMatch(coupon -> coupon.couponId().equals(couponId) && coupon.userId().equals(userId));
    }

    @Override
    public IssuedCoupon save(IssuedCoupon issuedCoupon) {
        issuedCoupons.put(issuedCoupon.id(), issuedCoupon);
        return issuedCoupon;
    }

    public void clear() {
        issuedCoupons.clear();
    }
}
