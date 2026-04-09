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

    public PrescriptionItemDto createItem(Long clinicId, PrescriptionItemDto dto){

        Prescription prescription = prescriptionRepository
                .findByIdAndClinicId(dto.getPrescriptionId(), clinicId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Prescription not found with id: " + dto.getPrescriptionId() + " for clinic: " + clinicId));

        PrescriptionItem item = new PrescriptionItem();

        item.setMedicine(dto.getMedicine());
        item.setDosage(dto.getDosage());
        item.setInstruction(dto.getInstruction());
        item.setClinic(prescription.getClinic());
        item.setPrescription(prescription);

        PrescriptionItem saved = prescriptionItemRepository.save(item);

        return modelMapper.map(saved, PrescriptionItemDto.class);
    }

    public List<PrescriptionItemDto> getItemsByPrescription(Long clinicId, Long prescriptionId){

        return prescriptionItemRepository.findByPrescriptionIdAndPrescriptionClinicId(prescriptionId, clinicId)
                .stream()
                .map(item -> modelMapper.map(item, PrescriptionItemDto.class))
                .collect(Collectors.toList());
    }

    public void deleteItem(Long clinicId, Long id){

        PrescriptionItem item = prescriptionItemRepository.findByIdAndPrescriptionClinicId(id, clinicId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found with id: " + id + " for clinic: " + clinicId));

        prescriptionItemRepository.delete(item);
    }

}
