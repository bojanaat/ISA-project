package com.example.demo.controller;

import com.example.demo.dto.request.SystemAdminRequest;
import com.example.demo.dto.response.SystemAdminResponse;
import com.example.demo.service.ISystemAdminService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/system-admins")
public class SystemAdminController {

    private final ISystemAdminService _iSystemAdminService;

    public SystemAdminController(ISystemAdminService iSystemAdminService) {
        _iSystemAdminService = iSystemAdminService;
    }

    @GetMapping("/{id}/system-admin")
    public SystemAdminResponse getSystemAdmin(@PathVariable UUID id) {
        return _iSystemAdminService.getSystemAdmin(id);
    }

    @PutMapping("{id}/system-admin")
    public SystemAdminResponse updateSystemAdmin(@PathVariable UUID id, @RequestBody SystemAdminRequest request) {
        return _iSystemAdminService.updateSystemAdmin(request, id);
    }

    @DeleteMapping("/{id}/system-admin")
    public void deleteSystemAdmin(@PathVariable UUID id) {
        _iSystemAdminService.deleteSystemAdmin(id);
    }

    @GetMapping
    public Set<SystemAdminResponse> getAllSystemAdmins() throws Exception {
        return _iSystemAdminService.getAllSystemAdmins();
    }
}
