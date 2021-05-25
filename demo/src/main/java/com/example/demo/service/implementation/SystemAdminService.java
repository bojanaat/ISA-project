package com.example.demo.service.implementation;

import com.example.demo.dto.request.SystemAdminRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.SystemAdminResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Patient;
import com.example.demo.model.SystemAdmin;
import com.example.demo.model.User;
import com.example.demo.repository.ISystemAdminRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.ISystemAdminService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.RequestType;
import com.example.demo.utils.UserType;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SystemAdminService implements ISystemAdminService {

    private final ISystemAdminRepository _iSystemAdminRepository;
    private final IUserRepository _iUserRepository;

    private final IUserService _iUserService;

    public SystemAdminService(ISystemAdminRepository iSystemAdminRepository, IUserRepository iUserRepository, IUserService iUserService) {
        _iSystemAdminRepository = iSystemAdminRepository;
        _iUserRepository = iUserRepository;
        _iUserService = iUserService;
    }

    @Override
    public SystemAdminResponse createSystemAdmin(SystemAdminRequest request) throws Exception {

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
        userRequest.setUserType(UserType.SYSTEM_ADMIN);

        UserResponse userResponse = _iUserService.createUser(userRequest);

        User user = _iUserRepository.findOneById(userResponse.getId());
        user.setId(userResponse.getId());

        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setUser(user);

        SystemAdmin savedSystemAdmin = _iSystemAdminRepository.save(systemAdmin);

        return mapSystemAdminToSystemAdminResponse(savedSystemAdmin);
    }

    @Override
    public SystemAdminResponse getSystemAdmin(UUID id) {

        SystemAdmin systemAdmin = _iSystemAdminRepository.findOneById(id);

        return mapSystemAdminToSystemAdminResponse(systemAdmin);
    }

    @Override
    public SystemAdminResponse updateSystemAdmin(SystemAdminRequest request, UUID id) {

        SystemAdmin systemAdmin = _iSystemAdminRepository.findOneById(id);

        systemAdmin.getUser().setFirstName(request.getFirstName());
        systemAdmin.getUser().setLastName(request.getLastName());
        systemAdmin.getUser().setAddress(request.getAddress());
        systemAdmin.getUser().setCity(request.getCity());
        systemAdmin.getUser().setCountry(request.getCountry());
        systemAdmin.getUser().setPhoneNumber(request.getPhoneNumber());

        SystemAdmin savedSystemAdmin = _iSystemAdminRepository.save(systemAdmin);

        return mapSystemAdminToSystemAdminResponse(savedSystemAdmin);
    }

    @Override
    public void deleteSystemAdmin(UUID id) {

        SystemAdmin systemAdmin = _iSystemAdminRepository.findOneById(id);

        systemAdmin.getUser().setDeleted(true);

        SystemAdmin savedSystemAdmin = _iSystemAdminRepository.save(systemAdmin);
    }

    @Override
    public Set<SystemAdminResponse> getAllSystemAdmins() throws Exception {
        Set<SystemAdmin> systemAdmins = _iSystemAdminRepository.findAllByUser_Deleted(false);

        if(systemAdmins.isEmpty()){
            throw new Exception("There aren't any system admins in the system.");
        }

        return systemAdmins.stream().map(systemAdmin -> mapSystemAdminToSystemAdminResponse(systemAdmin))
                .collect(Collectors.toSet());
    }

    private SystemAdminResponse mapSystemAdminToSystemAdminResponse(SystemAdmin systemAdmin) {
        SystemAdminResponse systemAdminResponse = new SystemAdminResponse();
        User user = systemAdmin.getUser();
        systemAdminResponse.setEmail(user.getEmail());
        systemAdminResponse.setId(systemAdmin.getId());
        systemAdminResponse.setAddress(user.getAddress());
        systemAdminResponse.setCity(user.getCity());
        systemAdminResponse.setCountry(user.getCountry());
        systemAdminResponse.setFirstName(user.getFirstName());
        systemAdminResponse.setLastName(user.getLastName());
        systemAdminResponse.setPhoneNumber(user.getPhoneNumber());
        return systemAdminResponse;
    }
}
