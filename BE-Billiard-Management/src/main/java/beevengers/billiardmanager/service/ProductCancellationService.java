package beevengers.billiardmanager.service;

import beevengers.billiardmanager.entity.product.ProductCancellation;

import java.util.List;

public interface ProductCancellationService {
    List<ProductCancellation> findAll();

    ProductCancellation save(ProductCancellation productCancellation);
}
