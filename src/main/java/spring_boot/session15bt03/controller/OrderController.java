package spring_boot.session15bt03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring_boot.session15bt03.model.Order;
import spring_boot.session15bt03.service.impl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String orders(
            String status,
            String sortBy,
            String direction,
            Integer page,
            Model model
    ) {

        if (status == null) status = "ALL";
        if (sortBy == null) sortBy = "createdDate";
        if (direction == null) direction = "desc";
        if (page == null || page < 1) page = 1;

        List<Order> allOrders =
                orderService.getOrders(status, sortBy, direction);

        int size = 3;

        int totalOrders = allOrders.size();

        int totalPages =
                (int) Math.ceil((double) totalOrders / size);

        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalOrders);

        List<Order> orders =
                allOrders.subList(start, end);

        // tạo list page
        List<Integer> listPages = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            listPages.add(i);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("listPages", listPages);
        model.addAttribute("totalOrders", totalOrders);

        return "orders";
    }
}
