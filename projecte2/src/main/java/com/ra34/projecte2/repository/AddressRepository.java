package com.ra34.projecte2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ra34.projecte2.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}