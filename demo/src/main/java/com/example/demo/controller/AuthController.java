package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.PatientRequest;
import com.example.demo.dto.request.SupplierRequest;
import com.example.demo.dto.request.SystemAdminRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.PatientResponse;
import com.example.demo.dto.response.SupplierResponse;
import com.example.demo.dto.response.SystemAdminResponse;
import com.example.demo.service.IAuthService;
import com.example.demo.service.IPatientService;
import com.example.demo.service.ISupplierService;
import com.example.demo.service.ISystemAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IPatientService _iPatientService;

    private final ISystemAdminService _iSystemAdminService;

    private final ISupplierService _iSupplierService;

    private final IAuthService _iAuthService;

    public AuthController(IPatientService iPatientService, ISystemAdminService iSystemAdminService, ISupplierService iSupplierService, IAuthService iAuthService) {
        _iPatientService = iPatientService;
        _iSystemAdminService = iSystemAdminService;
        _iSupplierService = iSupplierService;
        _iAuthService = iAuthService;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/suppliers")
    public SupplierResponse createSupplier(@RequestBody SupplierRequest request) throws Exception {
        return _iSupplierService.createSupplier(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        return _iAuthService.login(request);
    }
}
