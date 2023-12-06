package com.blog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.PaymentEntity;
import com.blog.payLoads.UserDto;
import com.blog.repository.PaymentRepository;
import com.blog.service.RazorpayService;
import com.blog.service.UserService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RazorpayService razorpayService;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@PostMapping("/request/{userId}")
	public ResponseEntity<String> initiatePayment(@RequestParam Double amount,@PathVariable int userId) throws RazorpayException
	{
		
		try {
		String orderId = this.razorpayService.initiatePayment(amount);
		UserDto user = this.userService.getUserById(userId);
		
		PaymentEntity payment=new PaymentEntity();
		payment.setAmount(amount);
		payment.setDonatedBy(user.getName());
		payment.setOrderId(orderId);
		
		this.paymentRepo.save(payment);
		
		return ResponseEntity.ok( orderId);
		}
		catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error in initiate payment");
        }
		
	}
	
	@GetMapping("/alldonations")
	public ResponseEntity<Integer> getAllDonations()
	{
		var allDonations = this.paymentRepo.getAllDonations();
		return new ResponseEntity<Integer>(allDonations,HttpStatus.OK);
	}

}
