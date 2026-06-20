package ru.vsu.sheluhin.carService.service;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.entity.DateSlot;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.UserRepository;
import ru.vsu.sheluhin.carService.repository.DateSlotRepository;
import ru.vsu.sheluhin.carService.response.DateSlotResponse;
import ru.vsu.sheluhin.carService.response.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateSlotService {

    private final DateSlotRepository dateSlotRepository;
    private final UserRepository userRepository;
    private final CommonProperties commonProperties;


    public DateSlotService(DateSlotRepository dateSlotRepository, UserRepository userRepository, CommonProperties commonProperties, UserService userService) {
        this.dateSlotRepository = dateSlotRepository;
        this.userRepository = userRepository;
        this.commonProperties = commonProperties;
    }

    public void createSlotsForNewMaster(int masterId) {
        if (LocalDate.now().plusDays(1).getDayOfMonth() != 1) {
            insertDateSlotsForMonth(LocalDate.now().plusDays(1), new ArrayList<Integer>(masterId));
        }
    }

    public List<DateSlotResponse> getDateSlots(LocalDate date) {
        return dateSlotRepository.findAllByLocalDate(date);
    }

    @Transactional
    public void setStatus(int dateSlotId, DateSlot.AccessStatus newStatus) {
        DateSlot dateSlotDb = dateSlotRepository.findDateSlotBySlotId(dateSlotId);
        if(DateSlot.AccessStatus.BOOK.equals(newStatus)) {
            if (dateSlotDb.getStatus().equals(DateSlot.AccessStatus.BOOK))
                throw new ValidationException("Время уже занято");
        }
        dateSlotRepository.updateAccessStatusById(newStatus, dateSlotId);
    }

    //@Scheduled(cron = "0 0 6 1 * *")
    public void refreshDateSlots() {
        dateSlotRepository.deleteAllByVisitDateBefore(LocalDateTime.now());
        List<Integer> masterIds = userRepository.findAuthUserIdsByUserTypeAndWorkStatus(User.UserType.MASTER,
                User.WorkStatus.WORK,
                Sort.by(commonProperties.getEmployerSortBy()));
        insertDateSlotsForMonth(LocalDate.now(), masterIds);
    }

    //@PostConstruct
    public void startDateSlots() {
        List<Integer> masterIds = userRepository.findAuthUserIdsByUserTypeAndWorkStatus(User.UserType.MASTER,
                User.WorkStatus.WORK,
                Sort.by(commonProperties.getEmployerSortBy()));

        insertDateSlotsForMonth(LocalDate.now().plusDays(1), masterIds);
        insertDateSlotsForMonth(LocalDate.now().plusDays(1).getDayOfMonth() == 1 ?
                LocalDate.of(LocalDate.now().plusDays(1).getYear(),
                        LocalDate.now().plusMonths(2).getMonth(),
                        1) :
                LocalDate.of(LocalDate.now().plusMonths(1).getYear(),
                        LocalDate.now().plusMonths(1).getMonth(),
                        1),
                masterIds);
        if (LocalDate.now().plusDays(1).getDayOfMonth() != 1) {
            insertDateSlotsForMonth(LocalDate.of(LocalDate.now().plusMonths(2).getYear(),
                    LocalDate.now().plusMonths(2).getMonth(),
                    1),
                    masterIds);
        }
    }

    private void insertDateSlotsForMonth(LocalDate startDate, List<Integer> masterIds) {
        LocalDate dateSlot = startDate;
        LocalTime timeSlot;
        DateSlot emptySlot = new DateSlot();
        for (int i = 0; i < dateSlot.getDayOfMonth(); ++i) {
            dateSlot = dateSlot.plusDays(1);
            for (int id : masterIds) {
                timeSlot = LocalTime.of(9, 0);
                emptySlot.setMasterId(id);
                emptySlot.setStatus(DateSlot.AccessStatus.FREE);
                while (!timeSlot.equals(LocalTime.of(12, 0))) {
                    emptySlot.setVisitDate(LocalDate.of(dateSlot.getYear(), dateSlot.getMonth(), dateSlot.getDayOfMonth()));
                    emptySlot.setVisitTime(timeSlot);
                    dateSlotRepository.save(emptySlot);
                    timeSlot = timeSlot.plusHours(1);
                }
                timeSlot = timeSlot.plusHours(1);
                while (!timeSlot.equals(LocalTime.of(18, 0))) {
                    emptySlot.setVisitDate(LocalDate.of(dateSlot.getYear(), dateSlot.getMonth(), dateSlot.getDayOfMonth()));
                    emptySlot.setVisitTime(timeSlot);
                    dateSlotRepository.save(emptySlot);
                    timeSlot = timeSlot.plusHours(1);
                }
            }
        }
    }
}
