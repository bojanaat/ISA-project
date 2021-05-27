package com.example.demo.service.implementation;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.NewPasswordRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Patient;
import com.example.demo.model.Supplier;
import com.example.demo.model.SystemAdmin;
import com.example.demo.model.User;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.ISupplierRepository;
import com.example.demo.repository.ISystemAdminRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IAuthService;
import com.example.demo.utils.RequestType;
import com.example.demo.utils.UserType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {

    private final IUserRepository _iUserRepository;
    private final IPatientRepository _iPatientRepository;
    private final ISystemAdminRepository _iSystemAdminRepository;
    private final ISupplierRepository _iSupplierRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(IUserRepository iUserRepository, IPatientRepository iPatientRepository, ISystemAdminRepository iSystemAdminRepository, ISupplierRepository iSupplierRepository, PasswordEncoder passwordEncoder) {
        _iUserRepository = iUserRepository;
        _iPatientRepository = iPatientRepository;
        _iSystemAdminRepository = iSystemAdminRepository;
        _iSupplierRepository = iSupplierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {

        User user = _iUserRepository.findOneByEmail(request.getEmail());

        if(user == null) {
            throw new Exception("Bad credentials.");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Bad credentials.");
        }

        if(user.isDeleted()) {
            throw new Exception("Your account has been deleted.");
        }

        if(user.getUserType().equals(UserType.PATIENT)) {
            Patient patient = _iPatientRepository.findOneByUser_Id(user.getId());
            if(patient.getRequestType().equals(RequestType.PENDING)) {
                throw new Exception("Your registration request hasn't been approved yet. It's pending.");
            } else if(patient.getRequestType().equals(RequestType.DENIED)) {
                throw new Exception("Your registration request has been denied.");
            } else if(patient.getRequestType().equals(RequestType.WAITING_FOR_ACTIVATION)) {
                throw new Exception("Your registration request has been " +
                        "approved but is waiting for activation. " +
                        "Please activate your account by accessing the " +
                        "activation link that has been sent to your email address.");
            }
        }

        boolean flag = false;

        if(user.getFirstTimeLoggedIn() == null){
            user.setFirstTimeLoggedIn(new Date());
            _iUserRepository.save(user);
            flag = true;
        }

        UserResponse userResponse = mapUserToUserResponse(user);
        userResponse.setSetNewPassword(flag);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserResponse(userResponse);

        return loginResponse;
    }

    @Override
    public LoginResponse setNewPassword(UUID id, NewPasswordRequest request) throws Exception {

        if(!request.getPassword().equals(request.getRePassword())) {
            throw new Exception("The passwords you entered do not match.");
        }

        Patient patient = _iPatientRepository.findOneById(id);
        SystemAdmin systemAdmin = _iSystemAdminRepository.findOneById(id);
        Supplier supplier = _iSupplierRepository.findOneById(id);

        UserResponse userResponse = null;

        if(patient != null) {
            patient.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
            patient.getUser().setFirstTimeLoggedIn(new Date());
            _iPatientRepository.save(patient);
            userResponse = mapUserToUserResponse(patient.getUser());
        } else if(systemAdmin != null) {
            systemAdmin.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
            systemAdmin.getUser().setFirstTimeLoggedIn(new Date());
            _iSystemAdminRepository.save(systemAdmin);
            userResponse = mapUserToUserResponse(systemAdmin.getUser());
        } else if(supplier != null) {
            supplier.getUser().setPassword(passwordEncoder.encode(request.getPassword()));
            supplier.getUser().setFirstTimeLoggedIn(new Date());
            _iSupplierRepository.save(supplier);
            userResponse = mapUserToUserResponse(supplier.getUser());
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserResponse(userResponse);

        return loginResponse;
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        UUID id = null;
        if (user.getUserType().equals(UserType.PATIENT)) {
            id = user.getPatient().getId();
        }else if(user.getUserType().equals(UserType.SYSTEM_ADMIN)){
            id = user.getSystemAdmin().getId();
        }else if(user.getUserType().equals(UserType.SUPPLIER)){
            id = user.getSupplier().getId();
        }
        userResponse.setId(id);
        userResponse.setAddress(user.getAddress());
        userResponse.setCity(user.getCity());
        userResponse.setCountry(user.getCountry());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setUserType(user.getUserType());
        if(user.getFirstTimeLoggedIn() == null) {
            userResponse.setSetNewPassword(false);
        } else {
            userResponse.setSetNewPassword(true);
        }

        return userResponse;
    }
}
