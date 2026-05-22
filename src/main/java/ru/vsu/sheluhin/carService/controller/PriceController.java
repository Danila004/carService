package ru.vsu.sheluhin.carService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Model;
import ru.vsu.sheluhin.carService.entity.Price;
import ru.vsu.sheluhin.carService.response.ServiceWithPriceResponse;
import ru.vsu.sheluhin.carService.service.PriceService;

import java.util.List;

@RestController
@RequestMapping(path = "/prices",
        produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public ResponseEntity<String> addPrices(@RequestBody List<ServiceWithPriceResponse> prices) {
        priceService.addPrice(prices);
        return ResponseEntity.ok("Услуги с ценами добавлены");
    }

    @PutMapping
    public ResponseEntity<Void> setPrice(@RequestBody Price price) {
        priceService.setPrice(price);
        return ResponseEntity.ok().build();
    }
}
