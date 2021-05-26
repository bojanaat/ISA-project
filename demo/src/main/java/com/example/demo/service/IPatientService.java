package com.example.demo.service;

import com.example.demo.dto.request.ApprovePatientRequest;
import com.example.demo.dto.request.DenyingRegistrationRequestMessage;
import com.example.demo.dto.request.PatientRequest;
import com.example.demo.dto.response.PatientResponse;

import java.util.Set;
import java.util.UUID;

public interface IPatientService {

    PatientResponse createPatient(PatientRequest request) throws Exception;

    PatientResponse getPatient(UUID id);

    PatientResponse updatePatient(PatientRequest request, UUID id);

    void deletePatient(UUID id);

    Set<PatientResponse> getAllPatients() throws Exception;

    Set<PatientResponse> getAllPendingRequests() throws Exception;

    PatientResponse approveRegistrationRequest(ApprovePatientRequest request);

    void denyRegistrationRequest(UUID patientId, DenyingRegistrationRequestMessage request);

    PatientResponse activateRegistration(ApprovePatientRequest request) throws Exception;
}
