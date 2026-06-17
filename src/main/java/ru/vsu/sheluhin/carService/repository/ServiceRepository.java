package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.sheluhin.carService.entity.Service;
import ru.vsu.sheluhin.carService.response.ServiceWithPriceResponse;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query("SELECT p.priceId, s.serviceId, s.serviceName, p.modelId, p.price, p.status " +
            "FROM Prices p " +
            "JOIN Services s ON s.serviceId = p.serviceId " +
            "WHERE p.modelId=:modelId")
    List<ServiceWithPriceResponse> findAllByModelId(Integer modelId);
}
