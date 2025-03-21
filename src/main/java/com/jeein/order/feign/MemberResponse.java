package com.jeein.order.feign;

import lombok.Getter;

@Getter
public class MemberResponse {
    private String id;
    private String name;
    private String email;
    private String nickname;
}
