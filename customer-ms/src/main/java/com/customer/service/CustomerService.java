package com.customer.service;

import com.customer.entity.CustomerEntity;
import com.customer.exception.LogicalException;
import com.customer.exception.RecordNotFoundException;
import com.customer.repository.CustomerRepository;
import com.customer.vo.CustomerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private CustomerRepository repository;

    public CustomerVO save(CustomerVO vo){
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(vo,customerEntity);

        repository.save(customerEntity);
        vo.setId(customerEntity.getId());
        return vo;
    }
    public CustomerVO update(CustomerVO vo){
        Optional<CustomerEntity> optional =repository.findById(vo.getId());
        if(optional.isPresent()){
            CustomerEntity entity= optional.get();
            BeanUtils.copyProperties(vo,entity);
            /*entity.setFirstName(vo.getFirstName());
            entity.setLastName(vo.getLastName());
            entity.setDateOfBirth(vo.getDateOfBirth());*/
            repository.save(entity);
        }else {
            throw new RecordNotFoundException("Customer not found with ID "+vo.getId());
        }
    return vo;
    }
    public String delete(Long id){
        try {
            repository.deleteById(id);
            return "Success";
        }catch (Exception e){
            throw new RecordNotFoundException("Failed to delete Id : "+id);
        }

    }
    public List<CustomerVO> getCustomers(){
        List<CustomerVO> voList = new ArrayList<>();
        List<CustomerEntity> list = repository.findAll();
        if(list!=null && !list.isEmpty()){
            list.stream().forEach(entity->{
                CustomerVO vo = new CustomerVO();
                BeanUtils.copyProperties(entity,vo);
                voList.add(vo);
            });
            return  voList;
        }
        return voList;
    }

    public List<CustomerVO> getCustomerByName(String name) {
        List<CustomerEntity> customerEntities= repository.findCustomerByName(name);
            if (!CollectionUtils.isEmpty(customerEntities)) {
                return  customerEntities.stream().map(entity -> {
                    CustomerVO vo = new CustomerVO();
                    BeanUtils.copyProperties(entity, vo);
                    return vo;
                }).collect(Collectors.toList());
            } else {
                throw new RecordNotFoundException("Customer not found for given Name:" + name);
            }


    }

}



