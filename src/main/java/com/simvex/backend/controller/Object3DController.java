package com.simvex.backend.controller;

import com.simvex.backend.dto.Object3DDetailResponseDto;
import com.simvex.backend.dto.Object3DListResponseDto;
import com.simvex.backend.service.Object3DService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class Object3DController {

    private final Object3DService object3DService;

    @GetMapping
    public ResponseEntity<List<Object3DListResponseDto>> getAllObjects() {
        List<Object3DListResponseDto> objects = object3DService.getAllObjects();
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object3DDetailResponseDto> getObjectDetail(
        @PathVariable Long id
    ) {
        Object3DDetailResponseDto objectDetail = object3DService.getObjectDetail(id);
        return ResponseEntity.ok(objectDetail);
    }
}
