package com.example.demo.controller;

import com.example.demo.dto.request.PharmacyRequest;
import com.example.demo.dto.response.PharmacyResponse;
import com.example.demo.service.IPharmacyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pharmacies")
public class PharmacyController {

    private final IPharmacyService _iPharmacyService;

    public PharmacyController(IPharmacyService iPharmacyService) {
        _iPharmacyService = iPharmacyService;
    }

    @PostMapping()
    public PharmacyResponse createPharmacy(@RequestBody PharmacyRequest request) throws Exception {
        return _iPharmacyService.createPharmacy(request);
    }
}
