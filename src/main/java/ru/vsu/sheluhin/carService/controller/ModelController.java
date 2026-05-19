package ru.vsu.sheluhin.carService.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.sheluhin.carService.entity.Model;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.request.AddModelRequest;
import ru.vsu.sheluhin.carService.request.AddServiceForModelRequest;
import ru.vsu.sheluhin.carService.service.ModelService;
import ru.vsu.sheluhin.carService.service.PriceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/models",
        produces = "application/json"
)
@CrossOrigin(origins = "http://localhost:5173")
public class ModelController {
    private final ModelService modelService;
    private final PriceService priceService;

    public ModelController(ModelService modelService, PriceService priceService) {
        this.modelService = modelService;
        this.priceService = priceService;
    }

//    @GetMapping
//    public List<Model> getModels(@RequestParam(required = false) String status) {
//        return modelService.getModels(status);
//    }

    @GetMapping(path = "/{brandId}")
    public List<Model> getModels(@PathVariable int brandId, @RequestParam(required = false) Optional<Status> status) {
        return modelService.getModels(brandId, status);
    }

    @PostMapping
    public Model addModel(@RequestBody AddModelRequest request) {//@RequestBody Model newModel, @RequestBody List<AddServiceForModelRequest> serviceList) {
        Model newModelDb = modelService.addModel(request.newModel());
        priceService.addPrice(newModelDb.getModelId(), request.serviceList());
        return newModelDb;
    }

    @PatchMapping(path = "/{modelId}/setStatus")
    public ResponseEntity<Void> setStatus(@PathVariable int modelId, @RequestBody Status newStatus) {
        modelService.setStatus(modelId, newStatus);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{modelId}/setReleaseDate")
    public ResponseEntity<Void> setReleaseDate(@PathVariable int modelId, @RequestBody LocalDate newDate) {
        modelService.setReleaseDate(modelId, newDate);
        return ResponseEntity.ok().build();
    }
}
