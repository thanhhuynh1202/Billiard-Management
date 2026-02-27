package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.Customer;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.CustomerRepository;
import beevengers.billiardmanager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer findById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public Customer save(Customer customer) throws ResourceHasAvailable {
        if (customerRepository.existsByPhone(customer.getPhone())) {
            throw new ResourceHasAvailable("phone", customer);
        }
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new ResourceHasAvailable("email", customer);
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customerRequest) throws ResourceNotFoundException, ResourceHasAvailable {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        if (customerRepository.existsByPhone(customerRequest.getPhone()) && !foundCustomer.getPhone().equals(customerRequest.getPhone())) {
            throw new ResourceHasAvailable("phone", customerRequest);
        }
        if (customerRepository.existsByEmail(customerRequest.getEmail()) && !foundCustomer.getEmail().equals(customerRequest.getEmail())) {
            throw new ResourceHasAvailable("email", customerRequest);
        }
        customerRequest.setId(id);
        return customerRepository.save(customerRequest);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        customerRepository.deleteById(id);
    }
}
