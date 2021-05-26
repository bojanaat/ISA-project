package com.example.demo.service;

import com.example.demo.dto.request.PharmacyAdminRequest;
import com.example.demo.dto.response.PharmacyAdminResponse;

public interface IPharmacyAdminService {

    PharmacyAdminResponse createPharmacyAdmin(PharmacyAdminRequest request) throws Exception;
}
