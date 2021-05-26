package com.example.demo.service.implementation;

import com.example.demo.dto.request.DermatologistRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.DermatologistResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Dermatologist;
import com.example.demo.model.Pharmacy;
import com.example.demo.model.User;
import com.example.demo.repository.IDermatologistRepository;
import com.example.demo.repository.IPharmacyRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IDermatologistService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.UserType;
import org.springframework.stereotype.Service;

@Service
public class DermatologistService implements IDermatologistService {

    private final IDermatologistRepository _iDermatologistRepository;
    private final IUserRepository _iUserRepository;
    private final IPharmacyRepository _iPharmacyRepository;

    private final IUserService _iUserService;

    public DermatologistService(IDermatologistRepository iDermatologistRepository, IUserRepository iUserRepository, IPharmacyRepository iPharmacyRepository, IUserService iUserService) {
        _iDermatologistRepository = iDermatologistRepository;
        _iUserRepository = iUserRepository;
        _iPharmacyRepository = iPharmacyRepository;
        _iUserService = iUserService;
    }

    @Override
    public DermatologistResponse createDermatologist(DermatologistRequest request) throws Exception {
        Pharmacy pharmacy = _iPharmacyRepository.findOneById(request.getPharmacyId());
        if(pharmacy == null){
            throw new Exception("You haven't assigned a pharmacy to the new created dermatologist.");
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(request.getEmail());
        userRequest.setPassword(request.getPassword());
        userRequest.setRePassword(request.getRePassword());
        userRequest.setFirstName(request.getFirstName());
        userRequest.setLastName(request.getLastName());
        userRequest.setAddress(request.getAddress());
        userRequest.setCity(request.getCity());
        userRequest.setCountry(request.getCountry());
        userRequest.setPhoneNumber(request.getPhoneNumber());
        userRequest.setUserType(UserType.DERMATOLOGIST);

        UserResponse userResponse = _iUserService.createUser(userRequest);

        User user = _iUserRepository.findOneById(userResponse.getId());
        user.setId(userResponse.getId());

        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setUser(user);
        dermatologist.setPharmacy(pharmacy);

        Dermatologist savedDermatologist = _iDermatologistRepository.save(dermatologist);

        return mapDermatologistToDermatologistResponse(savedDermatologist);
    }

    private DermatologistResponse mapDermatologistToDermatologistResponse(Dermatologist dermatologist) {
        DermatologistResponse dermatologistResponse = new DermatologistResponse();
        User user = dermatologist.getUser();
        dermatologistResponse.setEmail(user.getEmail());
        dermatologistResponse.setId(dermatologist.getId());
        dermatologistResponse.setAddress(user.getAddress());
        dermatologistResponse.setCity(user.getCity());
        dermatologistResponse.setCountry(user.getCountry());
        dermatologistResponse.setFirstName(user.getFirstName());
        dermatologistResponse.setLastName(user.getLastName());
        dermatologistResponse.setPhoneNumber(user.getPhoneNumber());
        return dermatologistResponse;
    }
}
