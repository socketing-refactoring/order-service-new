package com.jeein.order.dto.feign;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    private String id;
    private String name;
    private String email;
    private String nickname;

    public static MemberResponse of(String id, String name, String email, String nickname) {
        return new MemberResponse(id, name, email, nickname);
    }
}
