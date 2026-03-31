package ru.vsu.sheluhin.carService.service;
import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.entity.ServiceInOrder;
import ru.vsu.sheluhin.carService.repository.ServiceInOrderRepository;

import java.util.List;

@Service
public class ServiceInOrderService {
    private final ServiceInOrderRepository serviceInOrderRepository;

    public ServiceInOrderService(ServiceInOrderRepository serviceInOrderRepository) {
        this.serviceInOrderRepository = serviceInOrderRepository;
    }

    public void addServiceInOrder(int orderId, List<ru.vsu.sheluhin.carService.entity.Service> serviceList) {
        for (ru.vsu.sheluhin.carService.entity.Service service : serviceList) {
            ServiceInOrder newServiceInOrder = new ServiceInOrder();
            newServiceInOrder.setServiceId(service.getServiceId());
            newServiceInOrder.setOrderId(orderId);
            serviceInOrderRepository.save(newServiceInOrder);
        }
    }
}
