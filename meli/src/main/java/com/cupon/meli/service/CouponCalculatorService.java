package com.cupon.meli.service;
import java.util.*;

public class CouponCalculatorService {

    public static List<String> calculate(Map<String, Float> items, Float amount) {
        // Convertir los precios y el monto a enteros (centavos)
        int intAmount = Math.round(amount * 100);
        Map<String, Integer> intItems = new HashMap<>();

        // Convertir los precios de los items a centavos
        for (Map.Entry<String, Float> entry : items.entrySet()) {
            intItems.put(entry.getKey(), Math.round(entry.getValue() * 100)); // Precio en centavos
        }

        // Convertir la lista de ítems a una lista ordenada
        List<Map.Entry<String, Integer>> itemList = new ArrayList<>(intItems.entrySet());
        itemList.sort(Comparator.comparing(Map.Entry::getValue));

        // Dynamic programming approach
        int n = itemList.size();
        int[] dp = new int[intAmount + 1]; // El arreglo dp debe tener tamaño amount * 100 + 1
        int[] lastItemIndex = new int[intAmount + 1];

        // Inicialización del arreglo dp
        Arrays.fill(dp, 0);

        // Algoritmo de mochila 0/1
        for (int i = 0; i < n; i++) {
            int itemPrice = itemList.get(i).getValue();
            for (int j = intAmount; j >= itemPrice; j--) {
                if (dp[j] < dp[j - itemPrice] + itemPrice) {
                    dp[j] = dp[j - itemPrice] + itemPrice;
                    lastItemIndex[j] = i;
                }
            }
        }

        // Reconstruir los ítems seleccionados
        List<String> selectedItems = new ArrayList<>();
        int w = intAmount;

        while (w > 0) {
            int itemIndex = lastItemIndex[w];
            String itemId = itemList.get(itemIndex).getKey();
            selectedItems.add(itemId);
            w -= itemList.get(itemIndex).getValue();
        }

        return selectedItems;
    }

    public static void main(String[] args) {
        Map<String, Float> items = new HashMap<>();
        items.put("MLA1", 100f);
        items.put("MLA2", 210f);
        items.put("MLA3", 220f);
        items.put("MLA4", 80f);
        items.put("MLA5", 90f);

        Float amount = 500f;

        List<String> result = calculate(items, amount);
        System.out.println("Selected items: " + result);
    }
}
