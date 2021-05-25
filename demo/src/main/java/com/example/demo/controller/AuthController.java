package com.example.demo.controller;

import com.example.demo.dto.request.PatientRequest;
import com.example.demo.dto.request.SystemAdminRequest;
import com.example.demo.dto.response.PatientResponse;
import com.example.demo.dto.response.SystemAdminResponse;
import com.example.demo.service.IPatientService;
import com.example.demo.service.ISystemAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IPatientService _iPatientService;

    private final ISystemAdminService _iSystemAdminService;

    public AuthController(IPatientService iPatientService, ISystemAdminService iSystemAdminService) {
        _iPatientService = iPatientService;
        _iSystemAdminService = iSystemAdminService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/patients")
    public PatientResponse createPatient(@RequestBody PatientRequest request) throws Exception {
        return _iPatientService.createPatient(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/system-admins")
    public SystemAdminResponse createSystemAdmin(@RequestBody SystemAdminRequest request) throws Exception {
        return _iSystemAdminService.createSystemAdmin(request);
    }
}
