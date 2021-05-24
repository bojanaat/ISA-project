package com.example.demo.controller;

import com.example.demo.dto.request.PatientRequest;
import com.example.demo.dto.response.PatientResponse;
import com.example.demo.service.IPatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IPatientService _iPatientService;

    public AuthController(IPatientService iPatientService) {
        _iPatientService = iPatientService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/patients")
    public PatientResponse createPatient(@RequestBody PatientRequest request) throws Exception {
        return _iPatientService.createPatient(request);
    }
}
