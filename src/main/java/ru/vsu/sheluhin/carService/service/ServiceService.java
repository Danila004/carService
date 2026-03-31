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
import ru.vsu.sheluhin.carService.repository.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final CommonProperties commonProperties;

    public ServiceService(ServiceRepository serviceRepository, CommonProperties commonProperties) {
        this.serviceRepository = serviceRepository;
        this.commonProperties = commonProperties;
    }

    public ru.vsu.sheluhin.carService.entity.Service addService(ru.vsu.sheluhin.carService.entity.Service newService) {
        return serviceRepository.save(newService);
    }

    public Page<ru.vsu.sheluhin.carService.entity.Service> getServices(int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getServiceSortBy()));

        return serviceRepository.findAll(pageable);
    }

    public Page<ru.vsu.sheluhin.carService.entity.Service> getServices(Status status, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getServiceSortBy()));

        return serviceRepository.findServicesByStatusContaining(status, pageable);
    }

    @Transactional
    public void setStatus(int serviceId, Status newStatus) {
        serviceRepository.updateStatusById(newStatus, serviceId);
    }
}
