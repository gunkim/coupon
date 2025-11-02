package github.gunkim.coupon.web;

import github.gunkim.coupon.application.CouponService;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.UserId;
import github.gunkim.coupon.web.dto.IssueCouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/issue")
    public void issue(@RequestBody IssueCouponRequest request) {
        var couponId = CouponId.from(request.couponId());
        var userId = UserId.from(request.userId());

        couponService.issue(couponId, userId);
    }
}
