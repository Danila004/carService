package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.sheluhin.carService.entity.Brand;
import ru.vsu.sheluhin.carService.entity.Status;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Brands b SET b.status=:newStatus WHERE b.brandId=:brandId")
    void updateStatusById(Status newStatus, int brandId);

    @Query("SELECT b FROM Brands b WHERE b.status=:status")
    Page<Brand> findBrandsByStatusContaining(Status status, Pageable pageable);
}
