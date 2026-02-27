package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.response.ProductCancellationResponse;
import beevengers.billiardmanager.service.ProductCancellationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PrefixConfig.HISTORY)
@RequiredArgsConstructor
public class ProductCancellationController {
    private final ProductCancellationService productCancellationService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ProductCancellationResponse> productCancellationResponses = productCancellationService.findAll().stream().map((element) -> modelMapper.map(element, ProductCancellationResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(productCancellationResponses);

    }
}
