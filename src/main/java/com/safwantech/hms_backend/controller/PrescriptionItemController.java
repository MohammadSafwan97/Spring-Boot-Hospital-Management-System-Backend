package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.PrescriptionItemDto;
import com.safwantech.hms_backend.service.PrescriptionItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prescription-items")
public class PrescriptionItemController {

    private final PrescriptionItemService prescriptionItemService;

    @PostMapping
    public ResponseEntity<PrescriptionItemDto> createItem(
            @RequestParam Long clinicId,
            @RequestBody PrescriptionItemDto dto){

        return ResponseEntity.ok(
                prescriptionItemService.createItem(clinicId, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@RequestParam Long clinicId, @PathVariable Long id){

        prescriptionItemService.deleteItem(clinicId, id);

        return ResponseEntity.ok("Item deleted");
    }

}
