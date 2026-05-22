package ru.vsu.sheluhin.carService.service;

import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Price;
import ru.vsu.sheluhin.carService.repository.PriceRepository;
import ru.vsu.sheluhin.carService.request.AddServiceForModelRequest;
import ru.vsu.sheluhin.carService.response.ServiceWithPriceResponse;

import java.util.List;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public void addPrice(List<ServiceWithPriceResponse> prices) {
        for (ServiceWithPriceResponse service : prices) {
            Price newPrice = new Price(0,
                    service.serviceId(),
                    service.modelId(),
                    service.price(),
                    service.status());
            priceRepository.save(newPrice);
        }
    }

    public void setPrice(Price price) {
        priceRepository.save(price);
    }

}
