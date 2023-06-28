package com.senla.rental.controller;

import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.common.annotations.LogMethodExecution;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.CarOrderingRequestDto;
import com.senla.rental.dto.controller.PostmanCarOrderingRequestDto;
import com.senla.rental.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/{page}")
    public ResponseEntity<List<CarDto>> showCatalog(@PathVariable Integer page) {
        log.info("Trying show cars catalog...");
        return ResponseEntity.ok().body(catalogService.showAllFilteredCars(page, new CarsCatalogFilterForm()));
    }

    @PostMapping("/{page}")
    public ResponseEntity<List<CarDto>> showFilteredCatalog(@PathVariable Integer page,
                                                             @ModelAttribute CarsCatalogFilterForm filterForm) {
        log.info("Trying show cars catalog...");
        return ResponseEntity.ok().body(catalogService.showAllFilteredCars(page, filterForm));
    }

    @PostMapping("/request")
    public ResponseEntity<RequestDto> createRequest(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @ModelAttribute PostmanCarOrderingRequestDto dto) {
        log.info("Trying to reserve a car...");
        return ResponseEntity.ok().body(catalogService.createCarOrderingRequest(authorization.substring(7),
                new CarOrderingRequestDto(dto)));
    }

}
