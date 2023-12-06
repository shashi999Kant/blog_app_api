package com.blog.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blog.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;



@Service
public class RazerpayServiceImpl implements RazorpayService {

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    public String initiatePayment(Double amount) throws RazorpayException {
        // Initialize Razorpay client
    	Order order = null;
    	RazorpayClient razorpayClient = null;
    	try {
    	    razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
    	    // Razorpay API code
    	} catch (RazorpayException e) {
    		   JSONObject orderRequest = new JSONObject();
    	        orderRequest.put("amount", amount * 100); // Amount in paise
    	        orderRequest.put("currency", "INR");
    	        orderRequest.put("receipt", "blog_donation_" + System.currentTimeMillis());
    	        orderRequest.put("payment_capture", 1);

    	         order = razorpayClient.orders.create(orderRequest);
    	} 

     

        // Return the order ID
        return order.get("id");
    }
}
