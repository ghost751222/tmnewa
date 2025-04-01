package com.example.tmnewa.service;


import com.example.tmnewa.entity.DNProduct;
import com.example.tmnewa.repository.DNProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DNProductService extends LoginService {

    @Autowired
    DNProductRepository dnProductRepository;

    public List<DNProduct> findAll() {
        return dnProductRepository.findAll();
    }

    public DNProduct save(DNProduct dnProduct){
        return  dnProductRepository.save(dnProduct);
    }

    public Optional<DNProduct> findByProductName(String productName){
        return  dnProductRepository.findByProductName(productName);
    }
}
