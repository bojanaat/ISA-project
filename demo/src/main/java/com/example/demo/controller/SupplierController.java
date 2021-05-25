package com.example.demo.controller;

import com.example.demo.dto.request.SupplierRequest;
import com.example.demo.dto.response.SupplierResponse;
import com.example.demo.service.ISupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final ISupplierService _iSupplierService;

    public SupplierController(ISupplierService iSupplierService) {
        _iSupplierService = iSupplierService;
    }

    @GetMapping("{id}/supplier")
    public SupplierResponse getSupplier(@PathVariable UUID id) {
        return _iSupplierService.getSupplier(id);
    }

    @PutMapping("{id}/supplier")
    public SupplierResponse updateSupplier(@PathVariable UUID id, @RequestBody SupplierRequest request) {
        return _iSupplierService.updateSupplier(request, id);
    }

    @DeleteMapping("{id}/supplier")
    public void deleteSupplier(@PathVariable UUID id) {
        _iSupplierService.deleteSupplier(id);
    }

    @GetMapping
    public Set<SupplierResponse> getAllSuppliers() throws Exception {
        return _iSupplierService.getAllSuppliers();
    }
}
