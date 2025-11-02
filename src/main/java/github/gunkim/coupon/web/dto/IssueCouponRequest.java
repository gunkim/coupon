package github.gunkim.coupon.web.dto;

import java.util.UUID;

public record IssueCouponRequest(
        UUID couponId,
        //인증 & 인가를 따로 구현하지 않기 때문에 요청 값으로 유저 아이디를 받음.
        UUID userId
) {
}
