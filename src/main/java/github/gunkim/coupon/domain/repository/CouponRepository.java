package github.gunkim.coupon.domain.repository;

import github.gunkim.coupon.domain.model.coupon.Coupon;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface CouponRepository {
    Optional<Coupon> findById(CouponId couponId);

    @Component
    class DummyCouponRepository implements CouponRepository {
        @Override
        public Optional<Coupon> findById(CouponId couponId) {
            return Optional.of(
                    new Coupon(
                            couponId,
                            "test",
                            "code",
                            BigDecimal.ZERO,
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            100,
                            0,
                            Set.of()
                    )
            );
        }
    }
}
