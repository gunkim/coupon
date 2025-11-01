package github.gunkim.coupon.domain.model.condition;

import github.gunkim.coupon.domain.model.coupon.CouponContext;

public class UserGradeCondition implements CouponCondition {
    @Override
    public boolean isSatisfied(CouponContext context) {
        return false;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
