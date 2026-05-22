package ru.vsu.sheluhin.carService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.sheluhin.carService.configuration.CommonProperties;
import ru.vsu.sheluhin.carService.entity.Model;
import ru.vsu.sheluhin.carService.entity.Status;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.ModelRepository;
import ru.vsu.sheluhin.carService.response.ErrorCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final ModelRepository modelRepository;
    private final CommonProperties commonProperties;

    public ModelService(ModelRepository modelRepository, CommonProperties commonProperties) {
        this.modelRepository = modelRepository;
        this.commonProperties = commonProperties;
    }

    public Model addModel(Model newModel) {
        return modelRepository.save(newModel);
    }

    public List<Model> getModels(int brandId, Optional<Status> status) {
        List<Model> models = modelRepository.findAllByBrandId(brandId, Sort.by(commonProperties.getModelSortBy()));
        return status.map(value -> models.stream()
                .filter(model -> model.getStatus().equals(value))
                .collect(Collectors.toList())).orElse(models);
    }

    public void setModel(Model model) {
        modelRepository.save(model);
    }
}
