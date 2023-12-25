package com.emart.emart.controllers.checkout;

import com.emart.emart.dtos.CheckoutDto;
import com.emart.emart.models.Checkout;
import com.emart.emart.models.User;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.services.checkout.CheckoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emart.emart.utility.Utility.convertToResponseItemDto;
import static com.emart.emart.utility.Utility.convertToResponseMsgDto;

@RestController
@CrossOrigin
@RequestMapping("api/v1/cart")
public class CheckoutControllerImpl implements CheckoutController {

    private final Logger logger = LoggerFactory.getLogger(CheckoutControllerImpl.class);

    @Autowired
    private CheckoutService checkoutService;

    @Override
    @PostMapping("/{userId}")
    public ResponseEntity<Object> createCheckout(Checkout checkout, Long userId) {
        try {
            if (checkoutService.createCheckout(checkout, userId) == 0) {
                logger.info("checkout created successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Checkout created successfully", checkoutService.viewCheckout(userId)));
            }
            else if (checkoutService.createCheckout(checkout, userId) == 1) {
                logger.error("Active checkout found, cannot create a new one");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "An active checkout has found, cannot create a new one"));
            }
            else throw new Exception();
        } catch (Exception e) {
            logger.error("User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(convertToResponseMsgDto("400 Bad Request", "User not found"));
        }
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<Object> viewCheckout(Long userId) {
        try {
            CheckoutDto checkout = checkoutService.viewCheckout(userId);
            if (checkout != null) return ResponseEntity.status(HttpStatus.OK).body(convertToResponseItemDto("200 OK", "Checkout found", checkout));
            else {
                throw new Exception();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(convertToResponseMsgDto("404 Not Found", "No active checkout found"));
        }
    }

    @Override
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateCheckout(Checkout checkout, Long userId) {
        try {
            Long checkoutId = checkoutService.viewCheckout(userId).getCheckoutId();
            if (checkoutService.updateCheckout(checkout, userId) == 0) {
                logger.info("checkout updated successfully");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Checkout updated successfully", checkoutService.viewCheckoutById(checkoutId)));
            }
            else {
                logger.error("No active checkout found for the user");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 NOT FOUND", "No active checkout found for the user"));
            }
        }
        catch (Exception e) {
            logger.error("Unable to supply the requested amount", e);
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.startsWith("Unable to supply the requested amount from the product")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(convertToResponseMsgDto("406 NOT ACCEPTABLE", errorMessage));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(convertToResponseMsgDto("400 Bad Request", "Checkout not found"));
            }
        }
    }

    @Override
    @PostMapping("/products")
    public ResponseEntity<Object> addToCart(Long userId, Long productId, Integer noOfItems) {
        try
        {
            if(checkoutService.addToCart(userId, productId, noOfItems) == 0)
            {
                logger.info("Product added to the cart");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Product added to the cart", checkoutService.viewCheckout(userId)));
            }
            else if(checkoutService.addToCart(userId, productId, noOfItems) == 1)
            {
                logger.error("Product is already in the cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 Not Found", "Product is already in the cart"));
            }
            else if(checkoutService.addToCart(userId, productId, noOfItems) == 2)
            {
                logger.error("product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 Not Found", "Product not found"));
            }
            else if(checkoutService.addToCart(userId, productId, noOfItems) == 4)
            {
                logger.error("Insufficient stock to supply the requested amount");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(convertToResponseMsgDto("406 Not Acceptable", "Insufficient stock to supply the requested amount"));
            }
            else
            {
                logger.error("No active cart found for the user");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 Not Found", "No active cart found for the user"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error occurred");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(convertToResponseMsgDto("400 Bad Request", "Error occurred"));
        }
    }

    @Override
    @DeleteMapping("/products")
    public ResponseEntity<Object> removeFromCart(Long userId, Long productId) {
        try
        {
            if(checkoutService.removeFromCart(userId, productId) == 0)
            {
                logger.info("Product removed from the cart");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertToResponseItemDto("200 OK", "Product removed from the cart", checkoutService.viewCheckout(userId)));
            }
            else if(checkoutService.removeFromCart(userId, productId) == 1)
            {
                logger.error("Product is already in the cart");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 Not Found", "Product is not found the cart"));
            }
            else if(checkoutService.removeFromCart(userId, productId) == 2)
            {
                logger.error("product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 Not Found", "product not found"));
            }
            else
            {
                logger.error("No active cart found for the user");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(convertToResponseMsgDto("404 Not Found", "No active cart found for the user"));
            }
        }
        catch (Exception e)
        {
            logger.error("Error occurred");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(convertToResponseMsgDto("400 Bad Request", "Error occurred"));
        }
    }
}
