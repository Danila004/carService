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

    public Page<Brand> getBrands(int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getBrandSortBy()));

        return brandRepository.findAll(pageable);
    }

    public Page<Brand> getBrands(Status status, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getBrandSortBy()));

        return brandRepository.findBrandsByStatusContaining(status, pageable);
    }

    @Transactional
    public void setStatus(int brandId, Status newStatus) {
        brandRepository.updateStatusById(newStatus, brandId);
    }
}
