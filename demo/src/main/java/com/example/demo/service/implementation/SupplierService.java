package com.example.demo.service.implementation;

import com.example.demo.dto.request.SupplierRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.SupplierResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Pharmacy;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;
import com.example.demo.repository.IPharmacyRepository;
import com.example.demo.repository.ISupplierRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.ISupplierService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.UserType;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierService implements ISupplierService {

    private final ISupplierRepository _iSupplierRepository;
    private final IUserRepository _iUserRepository;
    private final IPharmacyRepository _iPharmacyRepository;

    private final IUserService _iUserService;

    public SupplierService(ISupplierRepository iSupplierRepository, IUserRepository iUserRepository, IPharmacyRepository iPharmacyRepository, IUserService iUserService) {
        _iSupplierRepository = iSupplierRepository;
        _iUserRepository = iUserRepository;
        _iPharmacyRepository = iPharmacyRepository;
        _iUserService = iUserService;
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) throws Exception {

        Pharmacy pharmacy = _iPharmacyRepository.findOneById(request.getPharmacyId());
        if(pharmacy == null){
            throw new Exception("You haven't assigned a pharmacy to the new created supplier.");
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
        userRequest.setUserType(UserType.SUPPLIER);

        UserResponse userResponse = _iUserService.createUser(userRequest);

        User user = _iUserRepository.findOneById(userResponse.getId());
        user.setId(userResponse.getId());

        Supplier supplier = new Supplier();
        supplier.setUser(user);
        supplier.setPharmacy(pharmacy);

        Supplier savedSupplier = _iSupplierRepository.save(supplier);

        return mapSupplierToSupplierResponse(savedSupplier);
    }

    @Override
    public SupplierResponse getSupplier(UUID id) {

        Supplier supplier = _iSupplierRepository.findOneById(id);

        return mapSupplierToSupplierResponse(supplier);
    }

    @Override
    public SupplierResponse updateSupplier(SupplierRequest request, UUID id) {

        Supplier supplier = _iSupplierRepository.findOneById(id);

        supplier.getUser().setFirstName(request.getFirstName());
        supplier.getUser().setLastName(request.getLastName());
        supplier.getUser().setAddress(request.getAddress());
        supplier.getUser().setCity(request.getCity());
        supplier.getUser().setCountry(request.getCountry());
        supplier.getUser().setPhoneNumber(request.getPhoneNumber());

        Supplier savedSupplier = _iSupplierRepository.save(supplier);

        return mapSupplierToSupplierResponse(savedSupplier);
    }

    @Override
    public void deleteSupplier(UUID id) {

        Supplier supplier = _iSupplierRepository.findOneById(id);

        supplier.getUser().setDeleted(true);

        _iSupplierRepository.save(supplier);
    }

    @Override
    public Set<SupplierResponse> getAllSuppliers() throws Exception {
        Set<Supplier> suppliers = _iSupplierRepository.findAllByUser_Deleted(false);

        if(suppliers.isEmpty()){
            throw new Exception("There aren't any suppliers in the system.");
        }

        return suppliers.stream().map(supplier -> mapSupplierToSupplierResponse(supplier))
                .collect(Collectors.toSet());
    }

    private SupplierResponse mapSupplierToSupplierResponse(Supplier supplier) {
        SupplierResponse supplierResponse = new SupplierResponse();
        User user = supplier.getUser();
        supplierResponse.setEmail(user.getEmail());
        supplierResponse.setId(supplier.getId());
        supplierResponse.setAddress(user.getAddress());
        supplierResponse.setCity(user.getCity());
        supplierResponse.setCountry(user.getCountry());
        supplierResponse.setFirstName(user.getFirstName());
        supplierResponse.setLastName(user.getLastName());
        supplierResponse.setPhoneNumber(user.getPhoneNumber());
        return supplierResponse;
    }
}
