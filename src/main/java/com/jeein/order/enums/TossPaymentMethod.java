package com.jeein.order.enums;

public enum TossPaymentMethod {
    VIRTUAL_ACCOUNT("가상계좌"),
    SIMPLE_PAYMENT("간편결제"),
    GAME_GIFT_CERTIFICATE("게임문화상품권"),
    BANK_TRANSFER("계좌이체"),
    BOOK_GIFT_CERTIFICATE("도서문화상품권"),
    CULTURE_GIFT_CERTIFICATE("문화상품권"),
    CARD("카드"),
    MOBILE_PAYMENT("휴대폰");

    private final String value;

    TossPaymentMethod(String value) {
        this.value = value;
    }
}
