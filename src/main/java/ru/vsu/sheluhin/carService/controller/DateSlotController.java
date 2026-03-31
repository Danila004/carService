package ru.vsu.sheluhin.carService.controller;

import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.DateSlot;
import ru.vsu.sheluhin.carService.service.DateSlotService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/dateSlots",
        produces = "application/json"
)
public class DateSlotController {

    private final DateSlotService dateSlotService;

    public DateSlotController(DateSlotService dateSlotService) {
        this.dateSlotService = dateSlotService;
    }

    @GetMapping
    public List<DateSlot> getDateSlots(@RequestParam LocalDate date) {
        return dateSlotService.getDateSlots(date);
    }

    @PatchMapping(path = "/{dateSlotId}/book")
    public ResponseEntity<Void> bookDateSlot(@PathVariable int dateSlotId) {
        dateSlotService.bookDateSlot(dateSlotId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{dateSlotId}/free")
    public ResponseEntity<Void> freeDateSlot(@PathVariable int dateSlotId) {
        dateSlotService.freeDateSlot(dateSlotId);
        return ResponseEntity.ok().build();
    }
}
