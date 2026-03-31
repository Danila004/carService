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

    public Page<Model> getModels(int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getModelSortBy()));

        return modelRepository.findAll(pageable);
    }

    public Page<Model> getModels(Status status, int page) {
        Pageable pageable = PageRequest.of(page,
                commonProperties.getPageSize(),
                Sort.by(commonProperties.getModelSortBy()));

        return modelRepository.findModelByStatusContaining(status, pageable);
    }

    @Transactional
    public void setStatus(int modelId, Status newStatus) {
        modelRepository.updateStatusById(newStatus, modelId);
    }

    public void setReleaseDate(int modelId, LocalDate newDate) {
        Model model = modelRepository.getModelByModelId(modelId);
        if (model.getReleaseDate().isAfter(newDate)){
            throw new ValidationException(ErrorCode.NEW_DATE_IS_BEFORE_EXIST_DATE);
        }
        model.setReleaseDate(newDate);
        modelRepository.save(model);
    }
}
