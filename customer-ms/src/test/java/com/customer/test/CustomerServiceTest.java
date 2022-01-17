package com.customer.test;

import com.customer.entity.CustomerEntity;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerService;
import com.customer.vo.CustomerVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@DataJpaTest
@RunWith(MockitoJUnitRunner.class)

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;


    @InjectMocks
    private CustomerService customerService;

    List<CustomerEntity> customerEntityList=new ArrayList<>();
    List<CustomerVO> customerVOS = new ArrayList<>();


    @Before
   public void setUp() {

        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName("Test1");
        entity.setDateOfBirth("23-02-1998");
        entity.setLastName("M");
        entity.setId(1L);
        customerEntityList.add(entity);
        CustomerEntity entity1 = new CustomerEntity();
        entity1.setFirstName("Test1");
        entity1.setDateOfBirth("22-02-2000");
        entity1.setLastName("Ma");
        entity1.setId(2L);
        customerEntityList.add(entity1);
        CustomerVO customerVO=new CustomerVO();
        customerVO.setFirstName("Test3");
        customerVO.setLastName("N");
        customerVO.setDateOfBirth("23-02-1998");
        customerVO.setId(1L);
        customerVOS.add(customerVO);


    }
    @Test
    public void saveOrUpdateCustomerTest(){

        when(customerRepository.save(any())).thenReturn(customerEntityList.get(0));
        CustomerVO customervo=customerService.save(customerVOS.get(0));
        assertNotNull(customervo);
        assertEquals( "Test3",customervo.getFirstName());
        customervo.setFirstName("Test2");
        when(customerRepository.findById(customervo.getId())).thenReturn(mappingVoToEntity(customervo));
        CustomerVO customerVO=customerService.update(customervo);
        assertNotNull(customerVO);
        assertEquals("Test2",customerVO.getFirstName());
    }


    @Test
    public void getCustomerByName(){
        when(customerRepository.findCustomerByName("Test1")).thenReturn(getEntities());
        List<CustomerVO> list=customerService.getCustomerByName("Test1");
        assertNotNull(list);
        assertEquals(2,list.size());
    }


    @Test
    public void deleteCustomerById(){
         customerRepository.deleteById(1L);//).then();
         customerService.delete(1L);
    }



    private Optional<CustomerEntity> mappingVoToEntity(CustomerVO customervo) {
        CustomerEntity customerEntity=new CustomerEntity();
        BeanUtils.copyProperties(customervo,customerEntity);
        return Optional.of(customerEntity);
    }
    private List<CustomerEntity> getEntities() {
        List<CustomerEntity> list=new ArrayList<>();
         for (CustomerEntity customerEntity : customerEntityList) {
            if ("Test1".equalsIgnoreCase(customerEntity.getFirstName() ))
                list.add(customerEntity);

        }

        return list;
    }



}
