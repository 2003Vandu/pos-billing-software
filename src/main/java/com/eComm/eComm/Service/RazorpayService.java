package com.eComm.eComm.Service;

import com.eComm.eComm.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface RazorpayService
{
   RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;

}
