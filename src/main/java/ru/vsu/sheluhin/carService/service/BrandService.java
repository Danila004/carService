package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.repository.BrandRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final CommonProperties commonProperties;

    public BrandService(BrandRepository brandRepository, CommonProperties commonProperties) {
        this.brandRepository = brandRepository;
        this.commonProperties = commonProperties;
    }

    public Brand addBrand(Brand newBrand) {
        return brandRepository.save(newBrand);
    }

    public List<Brand> getBrands(Optional<Status> status) {
        List<Brand> brands = brandRepository.findAll(Sort.by(commonProperties.getBrandSortBy()));
        return status.map(value -> brands.stream()
                .filter(brand -> brand.getStatus().equals(value))
                .collect(Collectors.toList())).orElse(brands);
    }

    public void setBrand(Brand brand) {
        brandRepository.save(brand);
    }
}
