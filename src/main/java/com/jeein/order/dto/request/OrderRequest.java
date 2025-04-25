package com.jeein.order.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderRequest {

    @NotEmpty(message = "토스 결제 주문 번호가 필요합니다.")
    private String tossOrderId;

    @NotEmpty(message = "공연 일정 정보가 필요합니다.")
    private String eventDatetimeId;

    @NotEmpty(message = "공연 정보가 필요합니다.")
    private String eventId;

    @NotEmpty(message = "좌석 정보가 필요합니다.")
    private List<String> seatIds;

    @NotNull(message = "결제 금액이 필요합니다.")
    @Min(value = 0, message = "결제 금액은 0원 이상이어야 합니다.")
    @Max(value = Integer.MAX_VALUE, message = "결제 금액이 너무 큽니다.")
    private int amount;

    @NotEmpty(message = "결제 인증키가 필요합니다.")
    private String paymentKey;
}
