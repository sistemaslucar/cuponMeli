package com.cupon.meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PriceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Este método simula la consulta a la base de datos en memoria H2
    public float getPrice(String itemId) {
        String sql = "SELECT price FROM items WHERE item_id = ?";
        try {
            // Usamos JdbcTemplate para consultar el precio desde la base de datos
            return jdbcTemplate.queryForObject(sql, new Object[]{itemId}, Float.class);
        } catch (Exception e) {
            return 0f;  // Si no existe el ítem o ocurre un error, retornamos 0f
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
}