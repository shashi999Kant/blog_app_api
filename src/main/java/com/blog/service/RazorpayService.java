package com.blog.service;

import com.razorpay.RazorpayException;

public interface RazorpayService {
	
		public String initiatePayment(Double amount) throws RazorpayException;

}
