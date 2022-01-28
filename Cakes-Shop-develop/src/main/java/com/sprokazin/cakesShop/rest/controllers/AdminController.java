package com.sprokazin.cakesShop.rest.controllers;

import com.sprokazin.cakesShop.goods.AvailabilityOfCake;
import com.sprokazin.cakesShop.goods.CakeService;
import com.sprokazin.cakesShop.orders.OrderService;
import com.sprokazin.cakesShop.orders.OrderStatusData;
import com.sprokazin.cakesShop.rest.dto.cake.CakeMoreInfo;
import com.sprokazin.cakesShop.rest.dto.order.OrderForAdmin;
import com.sprokazin.cakesShop.users.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = "/admin-panel")
public class AdminController {
    private OrderService orderService;
    private UserService userService;
    private CakeService cakeService;

    public AdminController(OrderService orderService, UserService userService, CakeService cakeService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cakeService = cakeService;
    }

    @GetMapping("/admin-menu")
    public ModelAndView getAdminMenu() {
        return new ModelAndView("menu");
    }

    /////////////Main parts///////////////////
    @GetMapping("/admin-orders")
    public ModelAndView getAdminOrdersList() {
        ModelAndView modelAndView = new ModelAndView("orders");
        modelAndView.addObject("orders", orderService.getOrders());
        return modelAndView;
    }

    @GetMapping("/admin-cakes")
    public ModelAndView getAdminCakesList() {
        ModelAndView modelAndView = new ModelAndView("cakes");
        modelAndView.addObject("cakes", cakeService.getCakes().getCakeList());
        return modelAndView;
    }

    @GetMapping("/admin-users")
    public ModelAndView getAdminUsersList() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getUsers());
        return modelAndView;
    }

    //////////////Orders work///////////////////////
    @GetMapping("/admin-get-order/{id}")
    public ModelAndView getAdminOrderById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("order");
        OrderForAdmin orderForAdmin = orderService.getOrderById(id);
        modelAndView.addObject("order", orderForAdmin);
        System.out.println(orderForAdmin);
        return modelAndView;
    }

    @PostMapping("/admin-get-order/{id}")
    public RedirectView editOrder(@PathVariable Long id, OrderStatusData orderStatusData) {
        orderService.changeStatus(id, orderStatusData.getStatus());
        return new RedirectView(String.format("/admin-panel/admin-get-order/%d", id));
    }

    @GetMapping("/admin-get-order/delete/{id}")
    public RedirectView deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new RedirectView("/admin-panel/admin-orders");
    }

    ////////////Cakes work/////////////////////////
    @GetMapping("/admin-get-cake/edit")
    public ModelAndView addingCake() {
        ModelAndView modelAndView = new ModelAndView("add-cake");
        modelAndView.addObject("cake", new CakeMoreInfo());
        return modelAndView;
    }

    @GetMapping(value = "cake/{id}")
    public ModelAndView getCakeById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cake");
        modelAndView.addObject("cake",cakeService.getCake(id));
        return modelAndView;
    }

    @GetMapping(value = "/admin-get-cake/delete/{id}")
    public RedirectView deleteCake(@PathVariable Long id){
        cakeService.deleteCake(id);
        return new RedirectView("/admin-panel/admin-cakes");
    }

    @PostMapping(value = "/admin-get-cake/edit")
    public RedirectView addCake(CakeMoreInfo cake){
        cake.setAvailabilityOfCake(AvailabilityOfCake.AVAILABLE);
        Long id = cakeService.addCake(cake);
        return new RedirectView("/admin-panel/cake/"+id.toString());
    }

}
