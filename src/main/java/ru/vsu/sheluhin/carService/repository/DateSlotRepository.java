package ru.vsu.sheluhin.carService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.sheluhin.carService.entity.DateSlot;
import ru.vsu.sheluhin.carService.entity.UnauthUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DateSlotRepository extends JpaRepository<DateSlot, Integer> {

    void deleteAllByVisitDateBefore(LocalDateTime visitDateBefore);

//    @Query("SELECT dt.visitDate, SUM(CASE WHEN dt.) FROM DateSlots dt " +
//            "WHERE date_trunc(dt.visitDate, 'day')=:day " +
//            "GROUP BY date_trunc(dt.visitDate, 'minute') ")
//    List<DateSlot> findAllByLocalDate(LocalDate day);

    @Query(value = "WITH FreeDateSlots AS " +
            "(SELECT * FROM DateSlots dt " +
            "WHERE dt.status LIKE 'FREE' " +
            "date_trunc(dt.visitDate, 'day')=:date) " +
            "                                      " +
            "SELECT * FROM FreeDateSlots fds " +
            "WHERE fds.masterId = (SELECT fds1.masterId" +
            "                      FROM FreedateSlots fds1" +
            "                      GROUP BY fds1.masterId" +
            "                      ORDER BY COUNT(*) DESC" +
            "                      LIMIT 1)",
    nativeQuery = true)
    List<DateSlot> findAllByLocalDate(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM DateSlots dt WHERE dt.slotId=:slotId FOR UPDATE",
    nativeQuery = true)
    DateSlot findDateSlotBySlotId(int slotId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DateSlots dt SET dt.status='FREE' WHERE dt.slotId=:slotId")
    void updateAccessStatusToFree(int slotId);
}
