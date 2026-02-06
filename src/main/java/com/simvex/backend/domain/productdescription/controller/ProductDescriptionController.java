package com.simvex.backend.domain.productdescription.controller;

import com.simvex.backend.domain.productdescription.dto.ProductDescriptionDto;
import com.simvex.backend.domain.productdescription.service.ProductDescriptionService;
import com.simvex.backend.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-descriptions")
@RequiredArgsConstructor
public class ProductDescriptionController {

    private final ProductDescriptionService productDescriptionService;

    // 특정 오브젝트의 제품 설명 조회
    @GetMapping("/object/{object3DId}")
    public ResponseEntity<ApiResponse<ProductDescriptionDto>> getByObject3DId(
            @PathVariable Long object3DId
    ) {
        ProductDescriptionDto description = productDescriptionService.getByObject3DId(object3DId);
        return ApiResponse.success(HttpStatus.OK, "제품 설명 조회 성공", description);
    }
}
