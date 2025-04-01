package com.example.tmnewa.repository;

import com.example.tmnewa.entity.DNProduct;
import com.example.tmnewa.entity.DNRoutine;
import com.example.tmnewa.vo.RequestQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface DNProductRepository extends JpaRepository<DNProduct, String> {


    Optional<DNProduct> findByProductName(String productName);
}
