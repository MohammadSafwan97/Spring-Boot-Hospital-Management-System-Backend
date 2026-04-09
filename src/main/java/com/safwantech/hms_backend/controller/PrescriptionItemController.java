package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.PrescriptionItemDto;
import com.safwantech.hms_backend.security.CurrentUserUtil;
import com.safwantech.hms_backend.service.PrescriptionItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prescription-items")
public class PrescriptionItemController {

    private final PrescriptionItemService prescriptionItemService;
    private final CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<PrescriptionItemDto> createItem(
            @RequestBody PrescriptionItemDto dto){

        return ResponseEntity.ok(
                prescriptionItemService.createItem(currentUserUtil.getCurrentClinicId(), dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id){

        prescriptionItemService.deleteItem(currentUserUtil.getCurrentClinicId(), id);

        return ResponseEntity.ok("Item deleted");
    }

}
