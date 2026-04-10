package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.InvoiceDto;
import com.safwantech.hms_backend.security.CurrentUserUtil;
import com.safwantech.hms_backend.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto dto) {
        dto.setClinicId(currentUserUtil.getCurrentClinicId());
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(dto));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices(currentUserUtil.getCurrentClinicId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(currentUserUtil.getCurrentClinicId(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDto dto) {
        dto.setClinicId(currentUserUtil.getCurrentClinicId());
        return ResponseEntity.ok(invoiceService.updateInvoice(currentUserUtil.getCurrentClinicId(), id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(currentUserUtil.getCurrentClinicId(), id);
        return ResponseEntity.noContent().build();
    }
}
