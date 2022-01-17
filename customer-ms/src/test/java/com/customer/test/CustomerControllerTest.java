package com.customer.test;


import com.customer.controller.CustomerController;
import com.customer.exception.RecordNotFoundException;
import com.customer.service.ICustomerService;
import com.customer.vo.CustomerVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper=new ObjectMapper();

    @MockBean
    private ICustomerService customerService;
    List<CustomerVO> customerVOS=new ArrayList<>();
    String json=null;

    @Before
    public void setUp(){
        CustomerVO customerVO=new CustomerVO();
        customerVO.setFirstName("Swathi");
        customerVO.setLastName("Rallapali");
        customerVO.setDateOfBirth("20-03-1998");
        customerVO.setId(1l);
        customerVOS.add(customerVO);
        CustomerVO customerVO1=new CustomerVO();
        customerVO1.setFirstName("Poojitha");
        customerVO1.setLastName("N");
        customerVO1.setDateOfBirth("20-03-1999");
        customerVO1.setId(2l);
        customerVOS.add(customerVO1);

        json="{\n" +
                "  \"firstName\": \"Swathi\",\n" +
                "  \"lastName\": \"Rallapali \",\n" +
                "  \"dateOfBirth\": \"20-03-1998\",\n" +
                "  \"id\": \"1\"\n" +
                "}";
    }

    @Test
    public void fetchCustomerByCustomerName() throws Exception {
        when(customerService.getCustomerByName("Swathi")).thenReturn(customerVOS);
        when(customerService.getCustomerByName(anyString())).thenReturn(anyList());
        mockMvc.perform(get("/api/customer/Swathi")).andExpect(status().isOk());

    }

    @Test(expected = Exception.class)
    public void fetchCustomerByCustomerNameInNullCaseTest() throws Exception {
        when(customerService.getCustomerByName("Swathi")).thenReturn(null);
        mockMvc.perform(get("/api/customer/Swathi"))
                .andExpect(jsonPath("$.message" ).value("Customer id Swathi does no exist"));
    }

    @Test(expected = Exception.class)
    public void fetchAllCustomerByCustomerNameExceptionTest() throws Exception {
        when(customerService.getCustomerByName("Swathi")).thenThrow(new RecordNotFoundException("record not there"));
        mockMvc.perform(get("/api/customer/Swathi")).andExpect(status().is5xxServerError());

    }

    @Test
    public void testCreateOrUpdateCustomer() throws Exception{
        when(customerService.save(customerVOS.get(0))).thenReturn(customerVOS.get(0));
        mockMvc.perform(post("/api/customer/")
                .contentType(MediaType.APPLICATION_JSON).content(json));
    }
    @Test
    public void deleteCustomerById() throws Exception {

        Mockito.when(customerService.delete(1L)).thenReturn("SUCCESS");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer/1", 1L))
                .andExpect(status().is2xxSuccessful());
       }
}
