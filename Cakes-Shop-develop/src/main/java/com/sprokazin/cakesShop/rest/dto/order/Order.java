package com.sprokazin.cakesShop.rest.dto.order;

import com.sprokazin.cakesShop.orders.DeliveryMethod;
import com.sprokazin.cakesShop.orders.OrderStatus;
import com.sprokazin.cakesShop.orders.PaymentMethod;
import com.sprokazin.cakesShop.rest.dto.Purchase;
import com.sprokazin.cakesShop.rest.dto.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Order's data")
@Validated
public class Order {

    @NotNull
    @Schema(description = "user info ", required = true)
    @JsonProperty("user")
    private User user;

    @NotNull
    @Schema(description = "delivery need", required = true)
    @JsonProperty("delivery")
    private DeliveryMethod deliveryMethod;

    @NotNull
    @Schema(description = "delivery address", required = true)
    @JsonProperty("deliveryAddress")
    private String address;

    @NotNull
    @Schema(description = "delivery time", required = true)
    @JsonProperty("delivery time")
    private LocalDateTime time;

    @NotNull
    @Schema(description = "payment status", required = true)
    @JsonProperty("payment")
    private PaymentMethod paymentMethod;

    @NotNull
    @Schema(description = "order status", required = true)
    @JsonProperty("orderStatus")
    private OrderStatus orderStatus;

    @NotNull
    @Schema(description = "purchases", required = true)
    @JsonProperty("purchases")
    private List<Purchase> purchases;
}
