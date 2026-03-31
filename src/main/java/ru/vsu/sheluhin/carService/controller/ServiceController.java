package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Service;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.service.BrandService;
import ru.vsu.sheluhin.carService.service.ServiceService;

@RestController
@RequestMapping(path = "/services",
        produces = "application/json"
)
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping(path = "/all")
    public Page<Service> getServices(@RequestParam(defaultValue = "0") int page) {
        return serviceService.getServices(page);
    }

    @GetMapping(path = "active")
    public Page<Service> getServices(@RequestParam Status status, @RequestParam(defaultValue = "0") int page) {
        return serviceService.getServices(status, page);
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
