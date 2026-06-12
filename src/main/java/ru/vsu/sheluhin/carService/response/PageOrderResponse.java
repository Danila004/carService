package ru.vsu.sheluhin.carService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.vsu.sheluhin.carService.entity.Order;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageOrderResponse {
    private List<OrderResponse> orders;
    private Integer totalPages;
    private Integer pageNumber;

    public static PageOrderResponse from(Page<Order> page) {
        return new PageOrderResponse(page.stream().map(PageOrderResponse::toOrderResponse).toList(),
                page.getNumber(),
                page.getTotalPages());
    }

    private static OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(order.getOrderId(),
                order.getBrandName(),
                order.getModelName(),
                order.getStateNumber(),
                order.getVisitDate(),
                order.getVisitTime(),
                order.getPrice(),
                order.getOrderStatus());
    }
}
