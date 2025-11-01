package github.gunkim.coupon.domain.model.condition;

import github.gunkim.coupon.domain.model.coupon.CouponContext;

interface CouponCondition {
    boolean isSatisfied(CouponContext context);
    String getDescription();
}