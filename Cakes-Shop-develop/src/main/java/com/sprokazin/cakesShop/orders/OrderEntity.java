package com.sprokazin.cakesShop.orders;

import com.sprokazin.cakesShop.users.UserEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ORDERINFO")
public class OrderEntity {
    @Setter(AccessLevel.NONE)
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserEntity user;

    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private List<PurchaseEntity> purchases;

    @Setter(AccessLevel.PROTECTED)
    private DeliveryMethod deliveryMethod;

    @Setter(AccessLevel.PROTECTED)
    private OrderStatus orderStatus;

    @Setter(AccessLevel.PROTECTED)
    private PaymentMethod paymentMethod;

    @Setter(AccessLevel.PROTECTED)
    private String address;

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime time;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderEntity that = (OrderEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}