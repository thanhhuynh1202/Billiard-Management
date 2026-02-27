package beevengers.billiardmanager.service.impl;

import beevengers.billiardmanager.entity.product.ProductCancellation;
import beevengers.billiardmanager.repo.ProductCancellationRepository;
import beevengers.billiardmanager.service.ProductCancellationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCancellationServiceImpl implements ProductCancellationService {
    private final ProductCancellationRepository productCancellationRepository;

    @Override
    public List<ProductCancellation> findAll() {
        return productCancellationRepository.findAll();
    }

    @Override
    public ProductCancellation save(ProductCancellation productCancellation) {
        return productCancellationRepository.save(productCancellation);
    }
}
