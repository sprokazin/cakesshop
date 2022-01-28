package com.sprokazin.cakesShop.orders;

import com.sprokazin.cakesShop.exceptions.OrderNotFoundException;
import com.sprokazin.cakesShop.goods.CakeRepository;
import com.sprokazin.cakesShop.rest.dto.order.Order;
import com.sprokazin.cakesShop.rest.dto.order.OrderForAdmin;
import com.sprokazin.cakesShop.users.UserRepository;
import com.sprokazin.cakesShop.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CakeRepository cakeRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PurchaseRepository purchaseRepository, UserService userService, UserRepository userRepository, CakeRepository cakeRepository) {
        this.orderRepository = orderRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.cakeRepository = cakeRepository;
    }

    public void addOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDeliveryMethod(order.getDeliveryMethod());
        orderEntity.setAddress(order.getAddress());
        orderEntity.setTime(order.getTime());
        orderEntity.setPaymentMethod(order.getPaymentMethod());
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setPurchases(order.getPurchases().stream()
                .map(purchase -> {
                    PurchaseEntity purchaseEntity = new PurchaseEntity();
                    purchaseEntity.setNumber(purchase.getNum());
                    purchaseEntity.setOrder(orderEntity);
                    purchaseEntity.setCake(cakeRepository.findById(purchase.getId()).orElseThrow(RuntimeException::new));
                    return purchaseEntity;
                }).collect(Collectors.toList()));
        orderEntity.setUser(userRepository.findUserEntityByNumber(order.getUser().getNumber()));
        orderRepository.saveAndFlush(orderEntity);
    }

    @Override
    public List<OrderForAdmin> getOrders() {
        return orderRepository.findAll().stream().map(orderEntity -> {
            OrderForAdmin order = new OrderForAdmin();
            order.setId(orderEntity.getId());
            order.setUsersName(orderEntity.getUser().getName());
            order.setUsersNumber(orderEntity.getUser().getNumber());
            order.setTime(orderEntity.getTime());
            order.setAddress(orderEntity.getAddress());
            order.setDeliveryMethod(orderEntity.getDeliveryMethod());
            order.setPaymentMethod(orderEntity.getPaymentMethod());
            order.setOrderStatus(orderEntity.getOrderStatus());

            Map<Long, Integer> tmp = new HashMap<>(Collections.emptyMap());
            for(int i = 0; i < orderEntity.getPurchases().size(); i ++) {
                tmp.put(orderEntity.getPurchases().get(i).getCake().getId(),
                        orderEntity.getPurchases().get(i).getOrder().getPurchases().get(i).getNumber());
            }
            order.setCakes(tmp);

            //далее ужасный код, за который мне будет стыдно утром (пишу это в 2 часа ночи), но BigDecimal арифметика
            //очень конченная
            BigDecimal priceCounter = BigDecimal.ZERO;
            List<BigDecimal> prices = new LinkedList<>();
            for(Map.Entry<Long, Integer> entry : order.getCakes().entrySet()) {
                prices.add(priceCounter.add(cakeRepository.findById(entry.
                        getKey()).stream().collect(Collectors.toList()).get(0).getPrice().multiply(BigDecimal.valueOf(
                        order.getCakes().get(entry.getKey())))));
            }
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal amt : prices) {
                sum = sum.add(amt);
            }
            order.setPrice(sum);
            //конец конченного кода (хотя он нигде не кончается лол)

            String currentName = "";
            for(Map.Entry<Long, Integer> entry : order.getCakes().entrySet()) {
                String everyCake = cakeRepository.findById(entry.getKey())
                        .stream().collect(Collectors.toList()).get(0).getName() +
                        " x" + order.getCakes().get(entry.getKey());
                currentName += everyCake;
                currentName += " ";
            }
            order.setAllCakesNames(currentName);
            return order;
        }).collect(Collectors.toList());
    }

    @Override
    public OrderForAdmin getOrderById(Long id) {
        for(int i = 0 ; i < getOrders().size(); i++) {
            if(Objects.equals(getOrders().get(i).getId(), id)) {
                return  getOrders().get(i);
            }
        }
        return null;
    }

    @Override
    public void changeStatus(Long id, OrderStatus orderStatus) {
//        orderRepository.updateStatus(id, orderStatus);
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order doesn't exist"));
        orderEntity.setOrderStatus(orderStatus);
        System.out.println(orderStatus);
        orderRepository.saveAndFlush(orderEntity);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
