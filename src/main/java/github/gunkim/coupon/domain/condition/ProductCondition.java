package github.gunkim.coupon.domain.condition;

import github.gunkim.coupon.domain.CouponContext;

public class ProductCondition implements CouponCondition {
    @Override
    public boolean isSatisfied(CouponContext context) {
        return false;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
