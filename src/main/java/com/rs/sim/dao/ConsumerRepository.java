package com.rs.sim.dao;

import com.rs.sim.dao.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, String> {

}

