package com.cupon.meli.service;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponCalculatorServiceImpl implements CouponCalculatorService {

    @Override
    public List<String> calculate(Map<String, Float> items, Float amount) {
        List<String> result = new ArrayList<>();
        float[] dp = new float[amount.intValue() + 1];
        Arrays.fill(dp, 0);

        // Mapa para recuperar el ítem correspondiente a una cantidad total en dp
        Map<Integer, String> lastItemIndex = new HashMap<>();

        // Convertimos el mapa de items a una lista para manejarlo más fácilmente
        List<Map.Entry<String, Float>> itemList = new ArrayList<>(items.entrySet());

        // Recorrer todos los ítems
        for (int i = 0; i < itemList.size(); i++) {
            for (int j = amount.intValue(); j >= itemList.get(i).getValue(); j--) {
                float newValue = dp[(int) (j - itemList.get(i).getValue())] + itemList.get(i).getValue();
                if (dp[j] < newValue) {
                    dp[j] = newValue;
                    lastItemIndex.put(j, itemList.get(i).getKey());  // Guardamos el índice del ítem correspondiente
                }
            }
        }

        // Recorremos el mapa de "últimos ítems" para construir la lista de ítems seleccionados
        float totalSpent = 0;
        for (int i = amount.intValue(); i > 0; i--) {
            if (lastItemIndex.containsKey(i)) {
                result.add(lastItemIndex.get(i));
                totalSpent += items.get(lastItemIndex.get(i));
                i -= items.get(lastItemIndex.get(i)).intValue(); // Reducimos el monto por el precio del ítem
            }
        }

        // Imprimimos el total gastado
        System.out.println("Total gastado: " + totalSpent);
        return result;
    }
}