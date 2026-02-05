package com.simvex.backend.domain.productdescription.controller;

import com.simvex.backend.domain.productdescription.dto.ProductDescriptionDto;
import com.simvex.backend.domain.productdescription.service.ProductDescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-descriptions")
@RequiredArgsConstructor
public class ProductDescriptionController {

    private final ProductDescriptionService productDescriptionService;

    @GetMapping("/object/{object3DId}")
    public ResponseEntity<ProductDescriptionDto> getByObject3DId(
        @PathVariable Long object3DId
    ) {
        ProductDescriptionDto description = productDescriptionService.getByObject3DId(object3DId);
        return ResponseEntity.ok(description);
    }
}
