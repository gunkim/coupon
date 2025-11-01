package github.gunkim.coupon.domain.model.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Coupon(
        CouponId id,
        String name,
        String code,
        CouponType type,
        BigDecimal discountValue,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int totalQuantity,
        int issuedQuantity
) {
}
