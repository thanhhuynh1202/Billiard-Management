package beevengers.billiardmanager.controller;

import beevengers.billiardmanager.config.PrefixConfig;
import beevengers.billiardmanager.dto.request.ProductCancellationRequest;
import beevengers.billiardmanager.dto.request.RoomOrderRequest;
import beevengers.billiardmanager.dto.response.ApiResponse;
import beevengers.billiardmanager.dto.response.RoomOrderResponse;
import beevengers.billiardmanager.entity.Customer;
import beevengers.billiardmanager.entity.invoice.Invoice;
import beevengers.billiardmanager.entity.invoice.InvoiceDetail;
import beevengers.billiardmanager.entity.product.Product;
import beevengers.billiardmanager.entity.product.ProductCancellation;
import beevengers.billiardmanager.entity.room.Room;
import beevengers.billiardmanager.entity.room.RoomOrder;
import beevengers.billiardmanager.entity.room.RoomProduct;
import beevengers.billiardmanager.exception.common.ResourceHasAvailable;
import beevengers.billiardmanager.exception.common.ResourceNotFoundException;
import beevengers.billiardmanager.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PrefixConfig.RESTAURANT)
@RequiredArgsConstructor
public class RestaurantController {
    private final RoomOrderService roomOrderService;
    private final RoomService roomService;
    private final RoomProductService roomProductService;
    private final ProductService productService;
    private final InvoiceService invoiceService;
    private final InvoiceDetailService invoiceDetailService;
    private final CustomerService customerService;
    private final ProductCancellationService productCancellationService;
    private final ModelMapper modelMapper;

    @PostMapping("/start")
    public ResponseEntity<List<RoomOrderResponse>> start(@RequestParam(value = "roomId") Long roomId) throws ResourceNotFoundException {
        List<RoomProduct> roomProductList = roomProductService.findAllByRoomId(roomId);
        Room room = roomService.findById(roomId);
        roomProductList.forEach(roomProduct -> {
            RoomOrder roomOrder = new RoomOrder();
            roomOrder.setRoom(room);
            roomOrder.setProduct(roomProduct.getProduct());
            if (roomProduct.getProduct().getHourly()) roomOrder.setQuantity(0.1);
            else roomOrder.setQuantity(1.0);
            roomOrderService.save(roomOrder);
        });
        List<RoomOrderResponse> roomOrderList = roomOrderService.findAllByRoomId(roomId).stream().map((roomOrder) -> modelMapper.map(roomOrder, RoomOrderResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok(roomOrderList);
    }

    @PostMapping
    public ResponseEntity<RoomOrderResponse> add(@RequestBody RoomOrderRequest roomOrderRequest) throws ResourceNotFoundException {
        Room room = roomService.findById(roomOrderRequest.getRoomId());
        Product product = productService.findById(roomOrderRequest.getProductId());

        boolean isExist = roomOrderService.existsByRoomAndProduct(room, product);

        if (isExist) {
            RoomOrder roomOrderFound = roomOrderService.findByRoomAndProduct(room, product);
            if (product.getHourly()) return ResponseEntity.badRequest().build();
            roomOrderFound.setQuantity(roomOrderFound.getQuantity() + 1.0);

            RoomOrderResponse roomOrderSave = modelMapper.map(roomOrderService.save(roomOrderFound), RoomOrderResponse.class);
            return ResponseEntity.ok(roomOrderSave);
        }

        if (product.getHourly()) roomOrderRequest.setQuantity(0.1);
        else roomOrderRequest.setQuantity(1.0);

        RoomOrder roomOrder = roomOrderService.save(modelMapper.map(roomOrderRequest, RoomOrder.class));
        RoomOrderResponse roomOrderSave = modelMapper.map(roomOrder, RoomOrderResponse.class);
        return ResponseEntity.ok(roomOrderSave);
    }

    @PutMapping
    public ResponseEntity<RoomOrderResponse> addMore(@RequestBody RoomOrderRequest roomOrderRequest) throws ResourceNotFoundException {
        RoomOrder roomOrder = modelMapper.map(roomOrderRequest, RoomOrder.class);
        roomOrder = roomOrderService.update(roomOrderRequest.getId(), roomOrder);
        RoomOrderResponse roomOrderResponse = modelMapper.map(roomOrder, RoomOrderResponse.class);
        return ResponseEntity.ok(roomOrderResponse);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> reduce(@RequestBody ProductCancellationRequest productCancellationRequest) throws ResourceNotFoundException {
        ProductCancellation productCancellation = modelMapper.map(productCancellationRequest, ProductCancellation.class);

        RoomOrder roomOrder = roomOrderService.findById(productCancellationRequest.getId());
        if (roomOrder.getQuantity() > productCancellationRequest.getQuantity()) {
            roomOrder.setQuantity(roomOrder.getQuantity() - productCancellationRequest.getQuantity());
            roomOrderService.update(roomOrder.getId(), roomOrder);
        } else {
            productCancellation.setQuantity(roomOrder.getQuantity());
            roomOrderService.delete(productCancellationRequest.getId());
        }
        productCancellationService.save(productCancellation);
        return ResponseEntity.ok(new ApiResponse(true, "Xóa thành công!"));
    }

    @PutMapping("/cashier/changeroom")
    public ResponseEntity<ApiResponse> changeRoom(@RequestParam(value = "currentRoomId") Long roomId, @RequestParam(value = "toRoomId") Long newRoomId) throws ResourceNotFoundException {
        List<RoomOrder> roomOrderList = roomOrderService.findAllByRoomId(roomId);

        for (RoomOrder roomOrder : roomOrderList) {
            roomOrder.setRoom(roomService.findById(newRoomId));
            roomOrderService.save(roomOrder);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Chuyển phòng thành công"));
    }

    @PutMapping("/cashier/splitroom")
    public ResponseEntity<ApiResponse> splitRoom(@RequestParam(value = "toRoomId") Long newRoomId, @RequestBody List<RoomOrderRequest> roomOrderRequests) throws ResourceNotFoundException {
        Room newRoom = roomService.findById(newRoomId);
        roomOrderRequests.forEach(roomOrderRequest -> {
            RoomOrder roomOrder = modelMapper.map(roomOrderRequest, RoomOrder.class);
            roomOrder.setRoom(newRoom);
            roomOrderService.save(roomOrder);
        });
        return ResponseEntity.ok(new ApiResponse(true, "Tách phòng thành công"));
    }

    @PostMapping("/cashier/setcustomer")
    public ResponseEntity<ApiResponse> setCustomer(@RequestParam(value = "roomId") Long roomId, @RequestParam(value = "customerId") Long customerId) throws ResourceNotFoundException, ResourceHasAvailable {
        Room room = roomService.findById(roomId);
        Customer customer = customerService.findById(customerId);
        room.setCustomer(customer);
        roomService.save(room);
        return ResponseEntity.ok(new ApiResponse(true, "Thêm khách hàng vào bàn thành công"));
    }

    @DeleteMapping("/cashier/checkout/{roomId}")
    public ResponseEntity<ApiResponse> checkout(@PathVariable("roomId") Long roomId) throws ResourceNotFoundException, ResourceHasAvailable {
        List<RoomOrder> roomOrderList = roomOrderService.findAllByRoomId(roomId);
        if (roomOrderList == null) return ResponseEntity.notFound().build();
        if (roomOrderList.isEmpty()) return ResponseEntity.notFound().build();

        Room room = roomService.findById(roomId);
        room.setCustomer(null);
        roomService.save(room);

        Invoice invoice = new Invoice();
        invoice.setRoom(roomService.findById(roomId));
        invoice.setCustomer(roomService.findById(roomId).getCustomer());
        invoice = invoiceService.save(invoice);

        for (RoomOrder roomOrder : roomOrderList) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setOrderTimeStart(roomOrder.getCreatedAt());
            invoiceDetail.setOrderTimeStart(roomOrder.getCreatedAt());
            invoiceDetail.setProduct(roomOrder.getProduct());
            invoiceDetail.setPrice(roomOrder.getProduct().getPrice());

            if (roomOrder.getProduct().getHourly()) {
                double quantity = (System.currentTimeMillis() - roomOrder.getCreatedAt().getTime()) / 3600000.0;
                quantity = Math.round(quantity * 10) / 10.0;
                if (quantity < 0.1) quantity = 0.1;
                invoiceDetail.setQuantity(quantity);
            } else invoiceDetail.setQuantity(roomOrder.getQuantity());

            invoiceDetailService.save(invoiceDetail);
            roomOrderService.delete(roomOrder.getId());
        }
        return ResponseEntity.ok(new ApiResponse(true, "Thanh toán thành công"));
    }
}
