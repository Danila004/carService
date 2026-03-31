package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.service.BrandService;

@RestController
@RequestMapping(path = "/brands",
        produces = "application/json"
)
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(path = "/all")
    public Page<Brand> getBrands(@RequestParam(defaultValue = "0") int page) {
        return brandService.getBrands(page);
    }

    @GetMapping(path = "active")
    public Page<Brand> getBrands(@RequestParam Status status, @RequestParam(defaultValue = "0") int page) {
        return brandService.getBrands(status, page);
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
