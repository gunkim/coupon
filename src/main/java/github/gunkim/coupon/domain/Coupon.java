package github.gunkim.coupon.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Coupon(
        Long id,
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
