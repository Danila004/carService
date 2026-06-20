package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.sheluhin.carService.entity.DateSlot;
import ru.vsu.sheluhin.carService.response.DateSlotResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DateSlotRepository extends JpaRepository<DateSlot, Integer> {

    void deleteAllByVisitDateBefore(LocalDateTime visitDateBefore);

    @Query(value = """
            WITH FreeDateSlots AS
            (SELECT *,
            	ROW_NUMBER() OVER (PARTITION BY visit_time ORDER BY slot_id) AS rn
            FROM date_slots
            WHERE status LIKE 'FREE' AND visit_date=:findDate AND visit_time > CURRENT_TIME + INTERVAL '5 minutes')
            
            SELECT slot_id AS slotId, visit_time AS visitTime, master_id AS masterId
            FROM FreeDateSlots
            WHERE rn=1
            """,
    nativeQuery = true)
    List<DateSlotResponse> findAllByLocalDate(LocalDate findDate);

    @Query(value = "SELECT * FROM date_slots WHERE slot_id=:slotId FOR UPDATE",
    nativeQuery = true)
    DateSlot findDateSlotBySlotId(int slotId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DateSlots dt SET dt.status=:newStatus WHERE dt.slotId=:slotId")
    void updateAccessStatusById(DateSlot.AccessStatus newStatus, int slotId);
}
