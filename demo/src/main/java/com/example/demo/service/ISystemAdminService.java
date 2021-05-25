package com.example.demo.service;

import com.example.demo.dto.request.SystemAdminRequest;
import com.example.demo.dto.response.SystemAdminResponse;

import java.util.Set;
import java.util.UUID;

public interface ISystemAdminService {

    SystemAdminResponse createSystemAdmin(SystemAdminRequest request) throws Exception;

    SystemAdminResponse getSystemAdmin(UUID id);

    SystemAdminResponse updateSystemAdmin(SystemAdminRequest request, UUID id);

    void deleteSystemAdmin(UUID id);

    Set<SystemAdminResponse> getAllSystemAdmins() throws Exception;
}
