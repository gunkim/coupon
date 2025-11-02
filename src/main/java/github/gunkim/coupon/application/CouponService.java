package github.gunkim.coupon.application;

import github.gunkim.coupon.application.exception.CouponAlreadyIssuedException;
import github.gunkim.coupon.application.exception.CouponNotFoundException;
import github.gunkim.coupon.application.exception.UserNotFoundException;
import github.gunkim.coupon.domain.model.coupon.Coupon;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.UserId;
import github.gunkim.coupon.domain.model.issuance.IssuedCoupon;
import github.gunkim.coupon.domain.model.issuance.IssuedCouponId;
import github.gunkim.coupon.domain.repository.CouponRepository;
import github.gunkim.coupon.domain.repository.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    private static final String USER_NOT_FOUND_MESSAGE = "유저가 없습니다.";
    private static final String COUPON_NOT_FOUND_MESSAGE = "존재하지 않는 쿠폰입니다.";
    private static final String COUPON_ALREADY_ISSUED_MESSAGE = "이미 발급된 쿠폰입니다.";

    public IssuedCouponId issue(CouponId couponId, UserId userId) {
        validateUser(userId);
        log.info("쿠폰 발급을 시도합니다. 쿠폰ID: {}, 유저ID: {}", couponId.value(), userId.value());
        var coupon = getCouponById(couponId);
        checkIfCouponAlreadyIssued(couponId, userId);

        var issuedCoupon = saveIssuedCoupon(coupon, userId);
        log.info("쿠폰이 성공적으로 발급되었습니다. 발급된 쿠폰ID: {}", issuedCoupon.id().value());
        return issuedCoupon.id();
    }

    private void validateUser(UserId userId) {
        log.debug("유저 검증을 시작합니다. 유저ID: {}", userId != null ? userId.value() : "null");
        if (userId == null) {
            log.error("유저 검증에 실패했습니다. 유저가 null입니다");
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
        }
    }

    private Coupon getCouponById(CouponId couponId) {
        log.debug("쿠폰을 조회합니다. 쿠폰ID: {}", couponId.value());
        return couponRepository.findById(couponId)
                .orElseThrow(() -> {
                    log.error("쿠폰을 찾을 수 없습니다. 쿠폰ID: {}", couponId.value());
                    return new CouponNotFoundException(COUPON_NOT_FOUND_MESSAGE);
                });
    }

    private void checkIfCouponAlreadyIssued(CouponId couponId, UserId userId) {
        log.debug("쿠폰 중복 발급 여부를 확인합니다. 쿠폰ID: {}, 유저ID: {}", couponId.value(), userId.value());
        boolean couponIssued = issuedCouponRepository.existsByCouponIdAndUserId(couponId, userId);
        if (couponIssued) {
            log.error("이미 발급된 쿠폰입니다. 쿠폰ID: {}, 유저ID: {}", couponId.value(), userId.value());
            throw new CouponAlreadyIssuedException(COUPON_ALREADY_ISSUED_MESSAGE);
        }
    }

    private IssuedCoupon saveIssuedCoupon(Coupon coupon, UserId userId) {
        log.debug("발급된 쿠폰을 저장합니다. 쿠폰ID: {}, 유저ID: {}", coupon.id().value(), userId.value());
        IssuedCoupon issuedCoupon = issuedCouponRepository.save(IssuedCoupon.issued(coupon, userId));
        log.debug("발급된 쿠폰이 저장되었습니다. 발급된 쿠폰ID: {}", issuedCoupon.id().value());
        return issuedCoupon;
    }
}