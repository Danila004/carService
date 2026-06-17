package ru.vsu.sheluhin.carService.controller;

import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.DateSlot;
import ru.vsu.sheluhin.carService.response.DateSlotResponse;
import ru.vsu.sheluhin.carService.service.DateSlotService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/dateSlots",
        produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
public class DateSlotController {

    private final DateSlotService dateSlotService;

    public DateSlotController(DateSlotService dateSlotService) {
        this.dateSlotService = dateSlotService;
    }

    @GetMapping
    public List<DateSlotResponse> getDateSlots(@RequestParam LocalDate date) {
        return dateSlotService.getDateSlots(date);
    }

    @PatchMapping(path = "/{slotId}")
    public ResponseEntity<Void> setStatus(@PathVariable int slotId, @RequestBody DateSlot.AccessStatus newStatus) {
        dateSlotService.setStatus(slotId, newStatus);
        return ResponseEntity.ok().build();
    }
}
