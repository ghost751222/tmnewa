package com.example.tmnewa.repository.qa;

import com.example.tmnewa.entity.qa.QaTaskJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QATaskJobRepository extends JpaRepository<QaTaskJob, String> {




}