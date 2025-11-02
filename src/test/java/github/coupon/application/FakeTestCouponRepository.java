package github.coupon.application;

import github.gunkim.coupon.domain.model.coupon.Coupon;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.repository.CouponRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FakeTestCouponRepository implements CouponRepository {
    private final Map<CouponId, Coupon> coupons = new HashMap<>();

    @Override
    public Optional<Coupon> findById(CouponId couponId) {
        return Optional.ofNullable(coupons.get(couponId));
    }

    public void save(Coupon coupon) {
        coupons.put(coupon.id(), coupon);
    }

    public Coupon createTestCoupon(CouponId couponId) {
        Coupon coupon = new Coupon(
                couponId,
                "테스트 쿠폰",
                "TEST-CODE",
                BigDecimal.valueOf(10000),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                100,
                0,
                Set.of()
        );
        save(coupon);
        return coupon;
    }

    public void clear() {
        coupons.clear();
    }
}
