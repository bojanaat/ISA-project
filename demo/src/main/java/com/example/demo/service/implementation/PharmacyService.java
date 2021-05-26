package com.example.demo.service.implementation;

import com.example.demo.dto.request.PharmacyRequest;
import com.example.demo.dto.response.PharmacyResponse;
import com.example.demo.model.Pharmacy;
import com.example.demo.model.User;
import com.example.demo.repository.IPharmacyRepository;
import com.example.demo.service.IPharmacyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PharmacyService implements IPharmacyService {

    private final IPharmacyRepository _iPharmacyRepository;

    public PharmacyService(IPharmacyRepository iPharmacyRepository) {
        _iPharmacyRepository = iPharmacyRepository;
    }

    @Override
    public PharmacyResponse createPharmacy(PharmacyRequest request) throws Exception {

        Pharmacy pharmacy = new Pharmacy();
        Set<Pharmacy> pharmacies = _iPharmacyRepository.findAllByDeleted(false);
        pharmacy.setName(request.getName());
        for(Pharmacy p : pharmacies) {
            if(p.getName().equals(pharmacy.getName())) {
                throw new Exception("The pharmacy you entered already exists.");
            }
        }

        pharmacy.setName(request.getName());
        pharmacy.setAddress(request.getAddress());
        pharmacy.setDescription(request.getDescription());
        pharmacy.setDeleted(false);

        _iPharmacyRepository.save(pharmacy);

        PharmacyResponse pharmacyResponse = new PharmacyResponse();
        pharmacyResponse.setId(pharmacy.getId());
        pharmacyResponse.setName(pharmacy.getName());
        pharmacyResponse.setAddress(pharmacy.getAddress());
        pharmacyResponse.setDescription(pharmacy.getDescription());

        return pharmacyResponse;
    }
}
