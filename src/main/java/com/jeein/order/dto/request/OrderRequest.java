package com.jeein.order.dto.request;

import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderRequest {
    private String memberId;
    private String eventId;
    //    private String eventTitle;
    private String eventDatetimeId;
    //    private String eventDatetime;
    private List<PaymentRequest> payments;
    private List<String> seatIds;
}
