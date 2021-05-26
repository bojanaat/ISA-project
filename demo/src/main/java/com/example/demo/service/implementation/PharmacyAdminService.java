package com.example.demo.service.implementation;

import com.example.demo.dto.request.PharmacyAdminRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.PharmacyAdminResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Pharmacy;
import com.example.demo.model.PharmacyAdmin;
import com.example.demo.model.User;
import com.example.demo.repository.IPharmacyAdminRepository;
import com.example.demo.repository.IPharmacyRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IPharmacyAdminService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.UserType;
import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService implements IPharmacyAdminService {

    private final IPharmacyAdminRepository _iPharmacyAdminRepository;
    private final IUserRepository _iUserRepository;
    private final IPharmacyRepository _iPharmacyRepository;

    private final IUserService _iUserService;

    public PharmacyAdminService(IPharmacyAdminRepository iPharmacyAdminRepository, IUserRepository iUserRepository, IPharmacyRepository iPharmacyRepository, IUserService iUserService) {
        _iPharmacyAdminRepository = iPharmacyAdminRepository;
        _iUserRepository = iUserRepository;
        _iPharmacyRepository = iPharmacyRepository;
        _iUserService = iUserService;
    }

    @Override
    public PharmacyAdminResponse createPharmacyAdmin(PharmacyAdminRequest request) throws Exception {

        Pharmacy pharmacy = _iPharmacyRepository.findOneById(request.getPharmacyId());
        if(pharmacy == null){
            throw new Exception("You haven't assigned a pharmacy to the new created pharmacy administrator.");
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(request.getFirstName());
        userRequest.setLastName(request.getLastName());
        userRequest.setAddress(request.getAddress());
        userRequest.setCity(request.getCity());
        userRequest.setCountry(request.getCountry());
        userRequest.setPhoneNumber(request.getPhoneNumber());
        userRequest.setEmail(request.getEmail());
        userRequest.setPassword(request.getPassword());
        userRequest.setRePassword(request.getRePassword());
        userRequest.setUserType(UserType.PHARMACY_ADMIN);

        UserResponse userResponse = _iUserService.createUser(userRequest);

        User user = _iUserRepository.findOneById(userResponse.getId());
        user.setId(userResponse.getId());

        PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
        pharmacyAdmin.setUser(user);
        pharmacyAdmin.setPharmacy(pharmacy);

        PharmacyAdmin savedPharmacyAdmin = _iPharmacyAdminRepository.save(pharmacyAdmin);

        return mapPharmacyAdminToPharmacyAdminResponse(savedPharmacyAdmin);
    }

    private PharmacyAdminResponse mapPharmacyAdminToPharmacyAdminResponse(PharmacyAdmin pharmacyAdmin) {
        PharmacyAdminResponse pharmacyAdminResponse = new PharmacyAdminResponse();
        User user = pharmacyAdmin.getUser();
        pharmacyAdminResponse.setEmail(user.getEmail());
        pharmacyAdminResponse.setId(pharmacyAdmin.getId());
        pharmacyAdminResponse.setAddress(user.getAddress());
        pharmacyAdminResponse.setCity(user.getCity());
        pharmacyAdminResponse.setCountry(user.getCountry());
        pharmacyAdminResponse.setFirstName(user.getFirstName());
        pharmacyAdminResponse.setLastName(user.getLastName());
        pharmacyAdminResponse.setPhoneNumber(user.getPhoneNumber());
        return pharmacyAdminResponse;
    }
}
