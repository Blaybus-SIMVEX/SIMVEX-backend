package com.simvex.backend.controller;

import com.simvex.backend.dto.ComponentDto;
import com.simvex.backend.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/components")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{componentId}")
    public ResponseEntity<ComponentDto> getComponentDetail(
        @PathVariable Long componentId
    ) {
        ComponentDto component = componentService.getComponentDetail(componentId);
        return ResponseEntity.ok(component);
    }
}
