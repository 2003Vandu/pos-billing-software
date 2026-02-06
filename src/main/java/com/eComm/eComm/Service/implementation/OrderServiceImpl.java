package com.eComm.eComm.Service.implementation;

import com.eComm.eComm.Repository.OrderEntityRepository;
import com.eComm.eComm.Service.OrderService;
import com.eComm.eComm.entity.OrderEnitity;
import com.eComm.eComm.entity.OrderItemEntity;
import com.eComm.eComm.io.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    // Dependency Injection

    private final OrderEntityRepository orderEntityRepository;


    // we need to convert request entity object into to response we get in frontend
    private OrderResponse convertToResponse(OrderEnitity newOrder) {

        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subtotal(newOrder.getSubtotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream()
                        .map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();

    }

    // when Request come from Http it get convert to OrderEntity pojo
    private OrderEnitity convertToOrderEntity(OrderRequest request) {

       return OrderEnitity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subtotal(request.getSubtotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();


    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {
      return  OrderResponse.OrderItemResponse.builder()
              .itemId(orderItemEntity.getItemId())
              .name(orderItemEntity.getName())
              .price(orderItemEntity.getPrice())
              .quantity(orderItemEntity.getQuantity())
              .build();

    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
       OrderEnitity newOrder = convertToOrderEntity(request);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH
                        ? PaymentDetails.PaymentStatus.COMPLETED
                        : PaymentDetails.PaymentStatus.PENDING );

        newOrder.setPaymentDetails(paymentDetails);

        List<OrderItemEntity> orderItems = request.getCartItems().stream()
                .map(this::convertToOrderItemEntity)
                        .collect(Collectors.toList());

        newOrder.setItems(orderItems);

       newOrder = orderEntityRepository.save(newOrder);

       return convertToResponse(newOrder);
    }


    @Override
    public void deleteOrder(String orderId) {

        OrderEnitity existingOrder = orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(()-> new RuntimeException("order not found"));

        orderEntityRepository.delete(existingOrder);

    }

    @Override
    public List<OrderResponse> getLatestOrder() {
       return orderEntityRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationRequest request) {
        OrderEnitity existingOrder = orderEntityRepository.findByOrderId(request.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found"));

        if(!verifyRazorpaySignature(request.getOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature())){

             throw new RuntimeException("Payment verification faild ");
        }

        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);

          existingOrder = orderEntityRepository.save(existingOrder);
          return convertToResponse(existingOrder);

    }

    // dashboard
    @Override
    public Double sumSalesByDate(LocalDate date) {
       return orderEntityRepository.sumSalesByDate(date);
    }
    // dashboard
    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepository.countByOrderDate(date);
    }
    // dashboard
    @Override
    public List<OrderResponse> findRecentOrders() {
                                                          // return the 5 order list of recent orders
        return orderEntityRepository.findRecentOrders(PageRequest.of(0,5))
                .stream()
                .map(orderEnitity -> convertToResponse(orderEnitity))
                .collect(Collectors.toList());
    }


    private boolean verifyRazorpaySignature(String orderId, String razorpayPaymentId, String razorpaySignature) {
       return true;

    }
}
