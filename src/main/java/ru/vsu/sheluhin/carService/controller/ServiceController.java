package ru.vsu.sheluhin.carService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Service;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.response.ServiceWithPriceResponse;
import ru.vsu.sheluhin.carService.service.ServiceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/services",
        produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<Service> getServices(@RequestParam Optional<Status> status) {
        return serviceService.getServices(status);
    }

    @GetMapping(path = "/{modelId}")
    public List<ServiceWithPriceResponse> getServices(@PathVariable Integer modelId, @RequestParam Optional<Status> status) {
        return serviceService.getModelServices(modelId, status);
    }

    @PostMapping
    public Service addService(@RequestBody Service newService) {
        return serviceService.addService(newService);
    }

    @PatchMapping(path = "/{serviceId}/setStatus")
    public ResponseEntity<Void> setStatus(@PathVariable int serviceId, @RequestBody Status newStatus) {
        serviceService.setStatus(serviceId, newStatus);
        return ResponseEntity.ok().build();
    }
}
