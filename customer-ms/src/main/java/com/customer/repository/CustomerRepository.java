package com.customer.repository;

import com.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository   extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "SELECT C.* FROM customer C WHERE C.FIRST_NAME = ?1 or C.LAST_NAME = ?1",nativeQuery = true)
    List<CustomerEntity> findCustomerByName(String name);
}
