package github.gunkim.coupon.application.exception;

public class CouponAlreadyIssuedException extends RuntimeException {
    public CouponAlreadyIssuedException(String message) {
        super(message);
    }
}
