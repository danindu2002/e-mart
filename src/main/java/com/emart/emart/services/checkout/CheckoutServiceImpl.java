package com.emart.emart.services.checkout;

import com.emart.emart.dtos.CheckoutDto;
import com.emart.emart.dtos.productCheckoutDtos.ProductCheckoutDto;
import com.emart.emart.mappers.UserMapper;
import com.emart.emart.models.Checkout;
import com.emart.emart.models.Product;
import com.emart.emart.models.ProductCheckout;
import com.emart.emart.models.User;
import com.emart.emart.repositories.CheckoutRepo;
import com.emart.emart.repositories.ProductCheckoutRepo;
import com.emart.emart.repositories.ProductRepo;
import com.emart.emart.repositories.UserRepo;
import com.emart.emart.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final Logger logger = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    @Autowired
    CheckoutRepo checkoutRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductCheckoutRepo productCheckoutRepo;

    @Override
    public int createCheckout(Checkout checkout, Long userId) {
        User user = userRepo.findByUserIdAndDeletedIsFalse(userId);
        Checkout existingCheckout = checkoutRepo.findByUserAndOrderedIsFalse(user);
        if (existingCheckout == null) {
            if (user != null) {
                logger.info("Checkout created");
                checkout.setUser(userRepo.findByUserIdAndDeletedIsFalse(userId));
                checkoutRepo.save(checkout);
                return 0;
            } else {
                logger.info("User not found");
                return 2;
            }
        } else {
            logger.info("Active checkout found, cannot create a new one");
            return 1;
        }
    }

    @Override
    public CheckoutDto viewCheckout(Long userId) {
        User user = userRepo.findByUserIdAndDeletedIsFalse(userId);
        logger.info("Checkout details fetched");
        return convertToCheckoutDto(checkoutRepo.findByUserAndOrderedIsFalse(user));
    }

    @Override
    public CheckoutDto viewCheckoutById(Long checkoutId) {
        logger.info("Checkout details fetched by checkout id");
        return convertToCheckoutDto(checkoutRepo.findByCheckoutId(checkoutId));
    }

    @Override
    @Transactional
    public int updateCheckout(Checkout checkout, Long userId) {
        try {
            User user = userRepo.findByUserIdAndDeletedIsFalse(userId);
            Checkout checkout1 = checkoutRepo.findByUserAndOrderedIsFalse(user);

            if (checkout1 != null) {
                checkout1.setCheckoutDate(checkout.getCheckoutDate());
                checkout1.setTotal(checkout.getTotal());
                checkout1.setOrdered(checkout.getOrdered());

                logger.info("Checkout updated as ordered");
                checkoutRepo.save(checkout1);

                if (checkout1.getOrdered()) {
                    List<String> unIssuedProductNames = new ArrayList<>();
                    for (ProductCheckout productCheckout : checkout1.getProductCheckouts())
                    {
                        Product product = productCheckout.getProduct();
                        if (product.getQuantity() >= productCheckout.getNoOfItems()) {
                            product.setQuantity(product.getQuantity() - productCheckout.getNoOfItems());
                            productRepo.save(product);
                            logger.info("Product issued");
                        }
                        else {
                            unIssuedProductNames.add(product.getProductName());
                        }
                    }
                    if (!unIssuedProductNames.isEmpty()) {
                        logger.error("Unable to supply the requested amount " + String.join(", ", unIssuedProductNames));
                        throw new Exception(" " + String.join(", ", unIssuedProductNames));
                    }
                }
                return 0;
            } else {
                logger.info("No active checkout found for the user");
                return 1;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int addToCart(Long userId, Long productId, Integer noOfItems) {
        User user = userRepo.findByUserIdAndDeletedIsFalse(userId);
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productId);
        Checkout checkout = checkoutRepo.findByUserAndOrderedIsFalse(user);

        if (checkout != null) {
            List<ProductCheckout> productCheckouts = checkout.getProductCheckouts();
            if (product != null) {
                if (product.getQuantity() >= noOfItems) {

                    ProductCheckout productCheckout = new ProductCheckout();
                    productCheckout.setProduct(product);
                    productCheckout.setCheckout(checkout);
                    productCheckout.setNoOfItems(noOfItems);
                    productCheckout.setSubTotal(noOfItems * product.getPrice());

                    ProductCheckout productCheckout1 = productCheckoutRepo.findByCheckoutAndProduct(checkout, product);
                    if (productCheckout1 == null) {
                        productCheckouts.add(productCheckout);
                        productRepo.save(product);

                        checkout.setProductCheckouts(productCheckouts);
                        checkoutRepo.save(checkout);

                        logger.info("Product added to the cart");
                        return 0;
                    } else {
                        logger.info("Product is already in the cart");
                        return 1;

                    }
                } else {
                    logger.info("Insufficient stock to supply the requested amount");
                    return 4;
                }
            } else {
                logger.info("Product not found");
                return 2;
            }
        } else {
            logger.info("No active cart found for the user");
            return 3;
        }
    }


    @Override
    public int removeFromCart(Long userId, Long productId) {
        User user = userRepo.findByUserIdAndDeletedIsFalse(userId);
        Product product = productRepo.findByProductIdAndDeletedIsFalse(productId);
        Checkout checkout = checkoutRepo.findByUserAndOrderedIsFalse(user);

        if (checkout != null) {
            List<ProductCheckout> productCheckouts = checkout.getProductCheckouts();
            if (product != null) {
                ProductCheckout productCheckout1 = productCheckoutRepo.findByCheckoutAndProduct(checkout, product);
                if (productCheckout1 != null) {
                    productCheckouts.remove(productCheckout1);
                    checkout.setProductCheckouts(productCheckouts);
                    checkoutRepo.save(checkout);

                    logger.info("product removed from the cart");
                    return 0;
                } else {
                    logger.info("product is not in the cart");
                    return 1;
                }
            } else {
                logger.info("product not found");
                return 2;
            }
        } else {
            logger.info("No active cart found for the user");
            return 3;
        }
    }

    @Override
    public CheckoutDto convertToCheckoutDto(Checkout checkout) {
        CheckoutDto dto = new CheckoutDto();

        dto.setCheckoutId(checkout.getCheckoutId());
        dto.setCheckoutDate(checkout.getCheckoutDate());
        dto.setTotal(checkout.getTotal());
        dto.setUser(UserMapper.userMapper.mapToUserDto(checkout.getUser()));
        dto.setProductsList(convertToProductCheckoutDtoList(checkout.getProductCheckouts()));
//        dto.setProductsList(ProductMapper.productMapper.maptoProductCheckoutDtoList(checkout.getProductList()));

        return dto;
    }

    @Override
    public ProductCheckoutDto convertToProductCheckoutDto(ProductCheckout productCheckout) {
        Product product = productCheckout.getProduct();
        ProductCheckoutDto dto = new ProductCheckoutDto();

        dto.setNoOfItems(productCheckout.getNoOfItems());
        dto.setSubTotal(productCheckout.getSubTotal());
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductCode(product.getProductCode());
        dto.setPrice(product.getPrice());
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());

        return dto;
    }

    @Override
    public List<ProductCheckoutDto> convertToProductCheckoutDtoList(List<ProductCheckout> productCheckoutList) {
        if (productCheckoutList == null) {
            return null;
        }

        List<ProductCheckoutDto> list = new ArrayList<ProductCheckoutDto>(productCheckoutList.size());
        for (ProductCheckout productCheckout : productCheckoutList) {
            list.add(convertToProductCheckoutDto(productCheckout));
        }

        return list;
    }

}
