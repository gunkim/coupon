package github.gunkim.coupon.domain.condition;

import github.gunkim.coupon.domain.CouponContext;

interface CouponCondition {
    boolean isSatisfied(CouponContext context);
    String getDescription();
}