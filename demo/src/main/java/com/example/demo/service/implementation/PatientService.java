package com.example.demo.service.implementation;

import com.example.demo.dto.request.PatientRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.PatientResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Patient;
import com.example.demo.model.User;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IPatientService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.RequestType;
import com.example.demo.utils.UserType;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private final IPatientRepository _iPatientRepository;
    private final IUserRepository _iUserRepository;

    private final IUserService _iUserService;

    public PatientService(IPatientRepository iPatientRepository, IUserRepository iUserRepository, IUserService iUserService) {
        _iPatientRepository = iPatientRepository;
        _iUserRepository = iUserRepository;
        _iUserService = iUserService;
    }

    @Override
    public PatientResponse createPatient(PatientRequest request) throws Exception {

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
        userRequest.setUserType(UserType.PATIENT);

        UserResponse userResponse = _iUserService.createUser(userRequest);

        User user = _iUserRepository.findOneById(userResponse.getId());
        user.setId(userResponse.getId());

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setRequestType(RequestType.PENDING);

        Patient savedPatient = _iPatientRepository.save(patient);

        return mapPatientToPatientResponse(savedPatient);
    }

    @Override
    public PatientResponse getPatient(UUID id) {

        Patient patient = _iPatientRepository.findOneById(id);

        return mapPatientToPatientResponse(patient);
    }

    @Override
    public PatientResponse updatePatient(PatientRequest request, UUID id) {

        Patient patient = _iPatientRepository.findOneById(id);
        patient.getUser().setFirstName(request.getFirstName());
        patient.getUser().setLastName(request.getLastName());
        patient.getUser().setAddress(request.getAddress());
        patient.getUser().setCity(request.getCity());
        patient.getUser().setCountry(request.getCountry());
        patient.getUser().setPhoneNumber(request.getPhoneNumber());

        Patient savedPatient = _iPatientRepository.save(patient);

        return mapPatientToPatientResponse(savedPatient);
    }

    @Override
    public void deletePatient(UUID id) {

        Patient patient = _iPatientRepository.findOneById(id);
        patient.getUser().setDeleted(true);

        Patient savedPatient = _iPatientRepository.save(patient);
    }

    @Override
    public Set<PatientResponse> getAllPatients() throws Exception {
        Set<Patient> patients = _iPatientRepository.findAllByRequestTypeAndUser_Deleted(RequestType.APPROVED, false);

        if(patients.isEmpty()){
            throw new Exception("There aren't any patients in the system.");
        }

        return patients.stream().map(patient -> mapPatientToPatientResponse(patient))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PatientResponse> getAllPendingRequests() throws Exception {

        Set<Patient> patients = _iPatientRepository.findAllByRequestType(RequestType.PENDING);

        if(patients.isEmpty()){
            throw new Exception("There aren't any pending registration requests.");
        }

        return patients.stream().map(patient -> mapPatientToPatientResponse(patient))
                .collect(Collectors.toSet());
    }

    private PatientResponse mapPatientToPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();
        User user = patient.getUser();
        patientResponse.setEmail(user.getEmail());
        patientResponse.setId(patient.getId());
        patientResponse.setAddress(user.getAddress());
        patientResponse.setCity(user.getCity());
        patientResponse.setCountry(user.getCountry());
        patientResponse.setFirstName(user.getFirstName());
        patientResponse.setLastName(user.getLastName());
        patientResponse.setPhoneNumber(user.getPhoneNumber());
        return patientResponse;
    }
}
