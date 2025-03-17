package com.example.tmnewa.repository;

import com.example.tmnewa.entity.DNRoutine;
import com.example.tmnewa.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DNRoutineRepository extends JpaRepository<DNRoutine, String> {


}
