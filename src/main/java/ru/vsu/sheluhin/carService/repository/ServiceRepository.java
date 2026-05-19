package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Service;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.entity.UnauthUser;
import ru.vsu.sheluhin.carService.response.ServiceWithPriceResponse;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Services s SET s.status=:newStatus WHERE s.serviceId=:serviceId")
    void updateStatusById(Status newStatus, int serviceId);

    @Query("SELECT s FROM Services s WHERE s.status=:status")
    Page<Service> findServicesByStatusContaining(Status status, Pageable pageable);

    @Query("SELECT s.serviceId, s.serviceName, p.price, s.status " +
            "FROM Prices p " +
            "JOIN Services s ON s.serviceId = p.serviceId " +
            "WHERE p.modelId=:modelId")
    List<ServiceWithPriceResponse> findAllByModelId(Integer modelId);
}
