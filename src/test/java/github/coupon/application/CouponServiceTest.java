package github.coupon.application;

import github.gunkim.coupon.application.CouponService;
import github.gunkim.coupon.application.exception.CouponAlreadyIssuedException;
import github.gunkim.coupon.application.exception.CouponNotFoundException;
import github.gunkim.coupon.application.exception.UserNotFoundException;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.UserId;
import github.gunkim.coupon.domain.model.issuance.IssuedCouponId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CouponServiceTest {
    private FakeTestCouponRepository couponRepository;
    private FakeTestIssuedCouponRepository issuedCouponRepository;
    private CouponService couponService;

    private CouponId couponId;
    private UserId userId;

    @BeforeEach
    void setUp() {
        couponRepository = new FakeTestCouponRepository();
        issuedCouponRepository = new FakeTestIssuedCouponRepository();
        couponService = new CouponService(couponRepository, issuedCouponRepository);

        couponId = CouponId.from(UUID.randomUUID());
        userId = UserId.from(UUID.randomUUID());
        couponRepository.createTestCoupon(couponId);
    }

    @Test
    void 쿠폰_발급_성공() {
        IssuedCouponId result = couponService.issue(couponId, userId);

        assertNotNull(result);
        assertTrue(issuedCouponRepository.existsByCouponIdAndUserId(couponId, userId));
    }

    @Test
    void 유저_아이디가_NULL일때_쿠폰_발급_실패() {
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> couponService.issue(couponId, null)
        );

        assertEquals("유저가 없습니다.", exception.getMessage());
        assertFalse(issuedCouponRepository.existsByCouponIdAndUserId(couponId, null));
    }

    @Test
    void 존재하지_않는_쿠폰_발급_실패() {
        CouponId nonExistentCouponId = CouponId.from(UUID.randomUUID());

        CouponNotFoundException exception = assertThrows(
                CouponNotFoundException.class,
                () -> couponService.issue(nonExistentCouponId, userId)
        );

        assertEquals("존재하지 않는 쿠폰입니다.", exception.getMessage());
        assertFalse(issuedCouponRepository.existsByCouponIdAndUserId(nonExistentCouponId, userId));
    }

    @Test
    void 이미_발급된_쿠폰_발급_실패() {
        // 첫 번째 발급
        couponService.issue(couponId, userId);

        // 두 번째 발급 시도
        CouponAlreadyIssuedException exception = assertThrows(
                CouponAlreadyIssuedException.class,
                () -> couponService.issue(couponId, userId)
        );

        assertEquals("이미 발급된 쿠폰입니다.", exception.getMessage());
        assertTrue(issuedCouponRepository.existsByCouponIdAndUserId(couponId, userId));
    }
}