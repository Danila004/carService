package ru.vsu.sheluhin.carService.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "carservice.common.properties")
@Component
public class CommonProperties {
    private final int pageSize = 10;
    private final String orderSortBy = "visitDate";
    private final String employerSortBy = "workStatus";
    private final String modelSortBy = "modelName";
    private final String brandSortBy = "brandName";
    private final String serviceSortBy = "serviceName";
}
