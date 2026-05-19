package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.service.BrandService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/brands",
        produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<Brand> getBrands(@RequestParam(required = false) Optional<Status> status) {
        return brandService.getBrands(status);
    }

    @PostMapping
    public Brand addBrand(@RequestBody Brand newBrand) {
        return brandService.addBrand(newBrand);
    }

    @PatchMapping(path = "/{brandId}/setStatus")
    public ResponseEntity<Void> setStatus(@PathVariable int brandId, @RequestBody Status newStatus) {
        brandService.setStatus(brandId, newStatus);
        return ResponseEntity.ok().build();
    }
}
