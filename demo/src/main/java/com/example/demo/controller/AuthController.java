package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IPatientService _iPatientService;

    private final ISystemAdminService _iSystemAdminService;

    private final ISupplierService _iSupplierService;

    private final IPharmacyAdminService _iPharmacyAdminService;

    private final IDermatologistService _iDermatologistService;

    private final IAuthService _iAuthService;

    public AuthController(IPatientService iPatientService, ISystemAdminService iSystemAdminService, ISupplierService iSupplierService, IPharmacyAdminService iPharmacyAdminService, IDermatologistService iDermatologistService, IAuthService iAuthService) {
        _iPatientService = iPatientService;
        _iSystemAdminService = iSystemAdminService;
        _iSupplierService = iSupplierService;
        _iPharmacyAdminService = iPharmacyAdminService;
        _iDermatologistService = iDermatologistService;
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
    @PostMapping("/pharmacy-admins")
    public PharmacyAdminResponse createPharmacyAdmin(@RequestBody PharmacyAdminRequest request) throws Exception {
        return _iPharmacyAdminService.createPharmacyAdmin(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/suppliers")
    public SupplierResponse createSupplier(@RequestBody SupplierRequest request) throws Exception {
        return _iSupplierService.createSupplier(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/dermatologists")
    public DermatologistResponse createDermatologist(@RequestBody DermatologistRequest request) throws Exception {
        return _iDermatologistService.createDermatologist(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        return _iAuthService.login(request);
    }

    @PostMapping("/{id}/new-password")
    public LoginResponse firstLogin(@PathVariable UUID id, @RequestBody NewPasswordRequest request) throws Exception {
        return _iAuthService.setNewPassword(id, request);
    }
}
