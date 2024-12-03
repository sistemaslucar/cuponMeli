package com.cupon.meli.service;
import java.util.*;

public class CouponCalculatorService {

    // Método que calcula la lista de ítems que el usuario puede comprar sin exceder el monto
    public static List<String> calculate(Map<String, Double> items, Double amount) {
        List<String> result = new ArrayList<>();
        double[] dp = new double[amount.intValue() + 1];  // dp[i] almacena el máximo valor alcanzable con un monto i
        Arrays.fill(dp, 0);

        // Mapa para recuperar el ítem correspondiente a una cantidad total en dp
        Map<Integer, String> lastItemIndex = new HashMap<>();

        // Convertimos el mapa de items a una lista para manejarlo más fácilmente
        List<Map.Entry<String, Double>> itemList = new ArrayList<>(items.entrySet());

        for (int i = 0; i < itemList.size(); i++) {
            for (int j = amount.intValue(); j >= itemList.get(i).getValue(); j--) {
                double newValue = dp[(int) (j - itemList.get(i).getValue())] + itemList.get(i).getValue();
                if (dp[j] < newValue) {
                    dp[j] = newValue;
                    lastItemIndex.put(j, itemList.get(i).getKey());  // Guardamos el índice del ítem correspondiente
                }
            }
        }

        // Recorremos el mapa de "últimos ítems" para construir la lista de ítems seleccionados
        double totalSpent = 0;
        for (int i = amount.intValue(); i > 0; i--) {
            if (lastItemIndex.containsKey(i)) {
                result.add(lastItemIndex.get(i));
                totalSpent += items.get(lastItemIndex.get(i));
                i -= items.get(lastItemIndex.get(i)).intValue(); // Reducimos el monto por el precio del ítem
            }
        }

        // Imprimimos el total gastado (esto es opcional)
        System.out.println("Total gastado: " + totalSpent);
        return result;
    }

    public static void main(String[] args) {
        // Usamos Double en lugar de Float
        Map<String, Double> items = new HashMap<>();
        items.put("MLA1", 100.0);
        items.put("MLA2", 210.0);
        items.put("MLA3", 220.0);
        items.put("MLA4", 80.0);
        items.put("MLA5", 90.0);

        Double amount = 600.0;

        List<String> selectedItems = calculate(items, amount);

        System.out.println("Ítems seleccionados: " + selectedItems);
    }


}
