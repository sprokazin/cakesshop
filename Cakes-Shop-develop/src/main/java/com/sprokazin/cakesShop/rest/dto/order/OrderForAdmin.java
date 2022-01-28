package com.sprokazin.cakesShop.rest.dto.order;

import com.sprokazin.cakesShop.orders.DeliveryMethod;
import com.sprokazin.cakesShop.orders.OrderStatus;
import com.sprokazin.cakesShop.orders.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Schema(description = "Order's admin data")
public class OrderForAdmin {
    private Long id;
    private String usersName;
    private String usersNumber;
    private String address;
    private LocalDateTime time;
    private DeliveryMethod deliveryMethod;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private Map<Long, Integer> cakes;//id and count of cake in order
    private BigDecimal price;
    private String allCakesNames;
}
