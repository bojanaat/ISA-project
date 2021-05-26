package com.example.demo.service;

import com.example.demo.dto.request.PharmacyRequest;
import com.example.demo.dto.response.PharmacyResponse;

public interface IPharmacyService {

    PharmacyResponse createPharmacy(PharmacyRequest request) throws Exception;
}
