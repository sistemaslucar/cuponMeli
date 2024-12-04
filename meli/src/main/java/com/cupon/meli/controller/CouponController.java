package com.cupon.meli.controller;
import com.cupon.meli.dto.CouponRequest;
import com.cupon.meli.dto.CouponResponse;
import com.cupon.meli.service.CouponCalculatorService;
import com.cupon.meli.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CouponController {

    @Autowired
    CouponCalculatorService couponCalculatorService;


    @Autowired
    PriceService priceService;

    @PostMapping("/couponMeli/coupon")
    public CouponResponse calculate(@RequestBody CouponRequest request) {
        // Map the list of item IDs to their prices (this should ideally come from an external service)
        Map<String, Float> itemPrices = new HashMap<>();

        for (String itemId : request.getItemIds()) {
            itemPrices.put(itemId, priceService.getPrice(itemId));
        }

        // Calculate the selected items using the algorithm
        List<String> selectedItems = couponCalculatorService.calculate(itemPrices, request.getAmount());

        // Calculate the total
        float total = 0;
        for (String itemId : selectedItems) {
            total += itemPrices.get(itemId);
        }

        if (selectedItems.isEmpty()) {
            throw new ItemNotFoundException("No items could be selected within the given amount.");
        }

        return new CouponResponse(selectedItems, total);
    }

    }




@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}