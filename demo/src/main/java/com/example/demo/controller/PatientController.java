package com.example.demo.controller;

import com.example.demo.dto.request.PatientRequest;
import com.example.demo.dto.response.PatientResponse;
import com.example.demo.service.IPatientService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService _iPatientService;

    public PatientController(IPatientService iPatientService) {
        _iPatientService = iPatientService;
    }

    @GetMapping("/{id}/patient")
    public PatientResponse getPatient(@PathVariable UUID id) {
        return _iPatientService.getPatient(id);
    }

    @PutMapping("/{id}/patient")
    public PatientResponse updatePatient(@RequestBody PatientRequest request, @PathVariable UUID id) {
        return _iPatientService.updatePatient(request, id);
    }

    @DeleteMapping("/{id}/patient")
    public void deletePatient(@PathVariable UUID id) {
        _iPatientService.deletePatient(id);
    }

    @GetMapping
    public Set<PatientResponse> getAllPatients() throws Exception {
        return _iPatientService.getAllPatients();
    }

    @GetMapping("/requests")
    public Set<PatientResponse> getAllPendingRequests() throws Exception {
        return _iPatientService.getAllPendingRequests();
    }
}
