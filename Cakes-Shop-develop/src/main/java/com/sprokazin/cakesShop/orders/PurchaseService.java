package com.sprokazin.cakesShop.orders;

import com.sprokazin.cakesShop.goods.CakeEntity;

public interface PurchaseService {
    void addPurchase(OrderEntity orderEntity, CakeEntity cakeEntity, Integer count);
}
