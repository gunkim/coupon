package github.gunkim.coupon.domain.model.coupon;

import github.gunkim.coupon.domain.model.condition.CouponCondition;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record Coupon(
        CouponId id,
        String name,
        String code,
        BigDecimal discountValue,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int totalQuantity,
        int issuedQuantity,
        Set<CouponCondition> conditions
) {
}
