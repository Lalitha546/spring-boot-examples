package com.customer.service;

import com.customer.vo.CustomerVO;

import java.util.List;

public interface ICustomerService {
    public CustomerVO save(CustomerVO vo);
    public CustomerVO update(CustomerVO vo);
    public String delete(Long id);
    public List<CustomerVO> getCustomers();

    public List<CustomerVO> getCustomerByName(String name);
}
