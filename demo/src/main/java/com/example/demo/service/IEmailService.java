package com.example.demo.service;

import com.example.demo.model.Patient;

public interface IEmailService {

    void approveRegistrationRequest(Patient patient);

    void denyRegistrationRequest(Patient patient, String reason);
}
