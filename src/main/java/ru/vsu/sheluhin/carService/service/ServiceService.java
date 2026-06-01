package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.repository.ServiceRepository;
import ru.vsu.sheluhin.carService.response.ServiceWithPriceResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ru.vsu.sheluhin.carService.entity.Service> getServices(Optional<Status> status) {
        List<ru.vsu.sheluhin.carService.entity.Service> services =
                serviceRepository.findAll(Sort.by(commonProperties.getServiceSortBy()));
        return status.map(value -> services.stream()
                .filter(service -> service.getStatus().equals(value))
                .collect(Collectors.toList())).orElse(services);
    }

    public List<ServiceWithPriceResponse> getModelServices(Integer modelId, Optional<Status> status) {
        List<ServiceWithPriceResponse> services = serviceRepository.findAllByModelId(modelId);
        return status.map(value -> services.stream()
                .filter(service -> service.status().equals(value))
                .collect(Collectors.toList())).orElse(services);
    }

    public ru.vsu.sheluhin.carService.entity.Service setService(ru.vsu.sheluhin.carService.entity.Service service) {
        return serviceRepository.save(service);
    }
}
