package com.eComm.eComm.Service;

import com.eComm.eComm.io.OrderRequest;
import com.eComm.eComm.io.OrderResponse;
import com.eComm.eComm.io.PaymentVerificationRequest;

import java.time.LocalDate;
import java.util.List;

public interface OrderService
{
   OrderResponse createOrder(OrderRequest request);

   void deleteOrder(String orderId);

   List<OrderResponse> getLatestOrder();

   OrderResponse verifyPayment(PaymentVerificationRequest request);

   Double sumSalesByDate(LocalDate date);

   Long countByOrderDate(LocalDate date);

   List<OrderResponse> findRecentOrders();

}

