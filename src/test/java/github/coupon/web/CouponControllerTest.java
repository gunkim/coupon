package github.coupon.web;

import github.coupon.application.FakeTestCouponRepository;
import github.coupon.application.FakeTestIssuedCouponRepository;
import github.gunkim.coupon.application.CouponService;
import github.gunkim.coupon.application.exception.CouponAlreadyIssuedException;
import github.gunkim.coupon.application.exception.CouponNotFoundException;
import github.gunkim.coupon.application.exception.UserNotFoundException;
import github.gunkim.coupon.domain.model.coupon.CouponId;
import github.gunkim.coupon.domain.model.external.UserId;
import github.gunkim.coupon.web.CouponController;
import github.gunkim.coupon.web.dto.IssueCouponRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO 예외 클래스를 테스트하지 않고, http response에 에러코드를 내려주고, 이를 테스트하는 형태로 변경해야 한다.
class CouponControllerTest {
    private MockMvc mockMvc;
    private JsonMapper jsonMapper;

    private FakeTestCouponRepository couponRepository;
    private FakeTestIssuedCouponRepository issuedCouponRepository;
    private CouponService couponService;
    private CouponController couponController;

    @BeforeEach
    void setUp() {
        jsonMapper = new JsonMapper();
        couponRepository = new FakeTestCouponRepository();
        issuedCouponRepository = new FakeTestIssuedCouponRepository();
        couponService = new CouponService(couponRepository, issuedCouponRepository);
        couponController = new CouponController(couponService);

        mockMvc = MockMvcBuilders.standaloneSetup(couponController).build();
    }

    @Test
    void 쿠폰_발급_성공() throws Exception {
        CouponId couponId = CouponId.from(UUID.randomUUID());
        UserId userId = UserId.from(UUID.randomUUID());
        couponRepository.createTestCoupon(couponId);

        IssueCouponRequest request = new IssueCouponRequest(couponId.value(), userId.value());

        mockMvc.perform(post("/api/v1/coupons/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void 유저_정보가_없을때_쿠폰_발급_실패() {
        CouponId couponId = CouponId.from(UUID.randomUUID());
        couponRepository.createTestCoupon(couponId);

        try {
            couponService.issue(couponId, null);
            fail("UserNotFoundException이 발생해야 합니다.");
        } catch (Exception e) {
            assertEquals(
                    UserNotFoundException.class,
                    e.getClass()
            );
        }
    }

    @Test
    void 쿠폰이_존재하지_않을때_쿠폰_발급_실패() {
        CouponId nonExistentCouponId = CouponId.from(UUID.randomUUID());
        UserId userId = UserId.from(UUID.randomUUID());

        try {
            couponService.issue(nonExistentCouponId, userId);
            fail("CouponNotFoundException이 발생해야 합니다.");
        } catch (Exception e) {
            assertEquals(
                    CouponNotFoundException.class,
                    e.getClass()
            );
        }
    }

    @Test
    void 이미_발급된_쿠폰일때_쿠폰_발급_실패() throws Exception {
        CouponId couponId = CouponId.from(UUID.randomUUID());
        UserId userId = UserId.from(UUID.randomUUID());
        couponRepository.createTestCoupon(couponId);

        IssueCouponRequest request = new IssueCouponRequest(couponId.value(), userId.value());

        mockMvc.perform(post("/api/v1/coupons/issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        try {
            couponService.issue(couponId, userId);
            fail("CouponAlreadyIssuedException이 발생해야 합니다.");
        } catch (Exception e) {
            assertEquals(
                    CouponAlreadyIssuedException.class,
                    e.getClass()
            );
        }
    }
}
