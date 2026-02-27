package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.ProductRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.ProductResponse;
import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PrefixConfig.PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> products = productService.findAll().stream().map((element) -> modelMapper.map(element, ProductResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<ProductResponse> findById(@RequestParam(value = "productId") Long id) throws ResourceNotFoundException {
        ProductResponse product = modelMapper.map(productService.findById(id), ProductResponse.class);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/manager")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest productRequest) throws ResourceHasAvailable {
        Product product = modelMapper.map(productRequest, Product.class);
        product = productService.save(product);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws ResourceNotFoundException {
        Product product = modelMapper.map(productRequest, Product.class);
        product = productService.update(id, product);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/manager/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) throws ResourceNotFoundException {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công"));
    }
}
