package com.customer.controller;


import com.customer.exception.RecordNotFoundException;
import com.customer.service.ICustomerService;
import com.customer.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/customer", produces = { MediaType.APPLICATION_JSON_VALUE })

public class CustomerController {
    @Autowired
   ICustomerService customerService;

    @GetMapping("/{name}")
    List<CustomerVO> getCustomersByName(@PathVariable String name) throws Exception
    {
        List<CustomerVO> vo= customerService.getCustomerByName(name);
        if(vo==null){
            throw new RecordNotFoundException("Customer id '" + name + "' does no exist");
        }
        return vo;
    }

    @GetMapping("/all")
    List<CustomerVO> getCustomers()
    {
        return customerService.getCustomers();

    }


    @PutMapping
    void update(@RequestBody CustomerVO customerDetails) {
        customerService.update(customerDetails);
    }
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {

        customerService.delete(id);
    }

    @PostMapping
    CustomerVO save(@RequestBody CustomerVO customerDetails){
        return customerService.save(customerDetails);

    }
}

