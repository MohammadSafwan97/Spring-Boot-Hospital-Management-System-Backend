package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PrescriptionItemDto;
import com.safwantech.hms_backend.entity.Prescription;
import com.safwantech.hms_backend.entity.PrescriptionItem;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.PrescriptionItemRepository;
import com.safwantech.hms_backend.repository.PrescriptionRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PrescriptionItemService {

    private final PrescriptionItemRepository prescriptionItemRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final ModelMapper modelMapper;

    public PrescriptionItemDto createItem(PrescriptionItemDto dto){

        Prescription prescription = prescriptionRepository
                .findById(dto.getPrescriptionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Prescription not found with id: " + dto.getPrescriptionId()));

        PrescriptionItem item = new PrescriptionItem();

        item.setMedicine(dto.getMedicine());
        item.setDosage(dto.getDosage());
        item.setInstruction(dto.getInstruction());
        item.setPrescription(prescription);

        PrescriptionItem saved = prescriptionItemRepository.save(item);

        return modelMapper.map(saved, PrescriptionItemDto.class);
    }

    public List<PrescriptionItemDto> getItemsByPrescription(Long prescriptionId){

        return prescriptionItemRepository.findByPrescriptionId(prescriptionId)
                .stream()
                .map(item -> modelMapper.map(item, PrescriptionItemDto.class))
                .collect(Collectors.toList());
    }

    public void deleteItem(Long id){

        PrescriptionItem item = prescriptionItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found"));

        prescriptionItemRepository.delete(item);
    }

}