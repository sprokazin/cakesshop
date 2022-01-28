package com.sprokazin.cakesShop.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
//    @Transactional
//    @Modifying
//    @Query("update OrderEntity u set u.orderStatus = :status where u.id = :id")
//    void updateStatus(@Param(value = "id") Long id, @Param(value = "status") OrderStatus status);
}
