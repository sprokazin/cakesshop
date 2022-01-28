package com.sprokazin.cakesShop.orders;

import org.springframework.data.jpa.repository.JpaRepository;

interface PurchaseRepository extends JpaRepository<PurchaseEntity,Long> {
}
