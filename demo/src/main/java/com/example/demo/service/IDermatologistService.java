package com.example.demo.service;

import com.example.demo.dto.request.DermatologistRequest;
import com.example.demo.dto.response.DermatologistResponse;

public interface IDermatologistService {

    DermatologistResponse createDermatologist(DermatologistRequest request) throws Exception;
}
