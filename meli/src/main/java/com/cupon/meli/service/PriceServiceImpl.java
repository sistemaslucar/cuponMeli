package com.cupon.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    // Este método simula la consulta a la base de datos en memoria H2
    @Override
    public float getPrice(String itemId) {
        String sql = "SELECT price FROM items WHERE item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{itemId}, Float.class);
        } catch (Exception e) {
            return 0f;  // Retorna 0 si no se encuentra el ítem en la base de datos
        }
    }

    // Método para inicializar los datos en la base de datos H2 en memoria al iniciar la aplicación
    @PostConstruct
    public void initDatabase() {
        // Crear la tabla de items
        jdbcTemplate.execute("CREATE TABLE items (item_id VARCHAR(10), price FLOAT)");

        // Insertar los datos de ejemplo en la tabla de items
        jdbcTemplate.update("INSERT INTO items (item_id, price) VALUES (?, ?)", "MLA1", 100f);
        jdbcTemplate.update("INSERT INTO items (item_id, price) VALUES (?, ?)", "MLA2", 210f);
        jdbcTemplate.update("INSERT INTO items (item_id, price) VALUES (?, ?)", "MLA3", 220f);
        jdbcTemplate.update("INSERT INTO items (item_id, price) VALUES (?, ?)", "MLA4", 80f);
        jdbcTemplate.update("INSERT INTO items (item_id, price) VALUES (?, ?)", "MLA5", 90f);
    }

    // Este método obtiene el precio de un ítem desde la API de Mercado Libre
    @Override
    public Float getItemPrice(String itemId) {
        // URL de la API de Mercado Libre con el ID del ítem
        String url = "https://api.mercadolibre.com/items/" + itemId;

        // Realizar la solicitud GET y obtener la respuesta
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("price")) {
                return Float.parseFloat(body.get("price").toString());
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el precio para el ítem " + itemId + ": " + e.getMessage());
        }
        return 0f;  // Retorna 0F si no se pudo obtener el precio
    }
}