package ru.vsu.sheluhin.carService.service;

import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.entity.Price;
import ru.vsu.sheluhin.carService.repository.PriceRepository;
import ru.vsu.sheluhin.carService.request.AddServiceForModelRequest;

import java.util.List;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public void addPrice(int modelId, List<AddServiceForModelRequest> serviceList) {
        for (AddServiceForModelRequest service : serviceList) {
            Price newPrice = new Price(0,
                    service.getServiceId(),
                    modelId,
                    service.getPrice());
//            newPrice.setServiceId(service.serviceId());
//            newPrice.setServiceId(modelId);
//            newPrice.setPrice(service.price());
            priceRepository.save(newPrice);
        }
    }
}
