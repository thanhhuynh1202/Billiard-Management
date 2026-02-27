package beevengers.billiardmanager.service.impl;


import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.repo.ProductRepository;
import beevengers.billiardmanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAllByDeletedFalse();
    }

    @Override
    public Product findById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
    }

    @Override
    public Product save(Product product) throws ResourceHasAvailable {
        if (productRepository.existsByNameAndDeletedFalse(product.getName())) {
            throw new ResourceHasAvailable("name", product);
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product productReq) throws ResourceNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        productReq.setId(id);
        return productRepository.save(productReq);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Product product = productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("id", id));
        product.setDeleted(true);
        productRepository.save(product);
    }
}
