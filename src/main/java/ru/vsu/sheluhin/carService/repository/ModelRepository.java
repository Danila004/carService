package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.sheluhin.carService.entity.Model;
import ru.vsu.sheluhin.carService.entity.Status;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Models m SET m.status=:newStatus WHERE m.modelId=:modelId")
    void updateStatusById(Status newStatus, int modelId);

    @Query("SELECT m FROM Models m WHERE m.status=:status")
    Page<Model> findModelByStatusContaining(Status status, Pageable pageable);

    Model getModelByModelId(int modelId);

    List<Model> findAllByBrandId(int brandId, Sort sort);
}
