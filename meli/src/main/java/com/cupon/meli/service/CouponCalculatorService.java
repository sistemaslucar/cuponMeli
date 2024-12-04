package com.cupon.meli.service;

import java.util.List;
import java.util.Map;

public interface CouponCalculatorService {
    public List<String> calculate(Map<String, Float> items, Float amount);
}