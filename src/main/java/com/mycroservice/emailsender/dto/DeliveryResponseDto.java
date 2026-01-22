package com.mycroservice.emailsender.dto;

import java.math.BigDecimal;

public record DeliveryResponseDto(long quantityEmails, long days, BigDecimal deliveryTax) {
}
