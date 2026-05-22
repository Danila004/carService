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

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(path = "/{brandId}")
    public List<Model> getModels(@PathVariable int brandId, @RequestParam(required = false) Optional<Status> status) {
        return modelService.getModels(brandId, status);
    }

    @PostMapping
    public Model addModel(@RequestBody Model newModel) {
        return modelService.addModel(newModel);
    }

    @PutMapping
    public ResponseEntity<Void> setModel(@RequestBody Model model) {
        modelService.setModel(model);
        return ResponseEntity.ok().build();
    }
}
