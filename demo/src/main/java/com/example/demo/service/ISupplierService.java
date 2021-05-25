package com.example.demo.service;

import com.example.demo.dto.request.SupplierRequest;
import com.example.demo.dto.response.SupplierResponse;

import java.util.Set;
import java.util.UUID;

public interface ISupplierService {

    SupplierResponse createSupplier(SupplierRequest request) throws Exception;

    SupplierResponse getSupplier(UUID id);

    SupplierResponse updateSupplier(SupplierRequest request, UUID id);

    void deleteSupplier(UUID id);

    Set<SupplierResponse> getAllSuppliers() throws Exception;
}
