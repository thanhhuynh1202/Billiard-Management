package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.CustomerRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.CustomerResponse;
import beevengers.billiardmanager.entity.Customer;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PrefixConfig.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> findAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<CustomerResponse> customers = customerService.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerResponse.class));
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<CustomerResponse> findById(@RequestParam(value = "customerId") Long id) throws ResourceNotFoundException {
        CustomerResponse customer = modelMapper.map(customerService.findById(id), CustomerResponse.class);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/cashier")
    public ResponseEntity<CustomerResponse> save(@RequestBody CustomerRequest customerRequest) throws ResourceHasAvailable {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        customer = customerService.save(customer);
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("/cashier/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        customer = customerService.update(id, customer);
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/cashier/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
        customerService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công"));
    }
}
