Cupon API - Instrucciones de Ejecución

Descripción
Esta API proporciona un servicio para calcular los ítems que un usuario
puede comprar sin exceder un monto determinado utilizando un cupón. 
Los precios de los ítems se obtienen desde una API externa de Mercado Libre.

La API tiene una forma de obtener los precios:


API de Mercado Libre: Se realiza una solicitud HTTP a la API de Mercado Libre para obtener 
el precio de los ítems en llamado uno a uno.


Requisitos
Java 11 o superior
Maven o Gradle (dependiendo de cómo esté configurado tu proyecto)
Spring Boot (este proyecto está basado en Spring Boot)
Base de datos H2 (en memoria, gestionada automáticamente por Spring Boot)
Instrucciones para ejecutar el proyecto
1. Clonar el Repositorio
   Primero, clona el repositorio a tu máquina local:

bash
Copiar código
git clone https://github.com/sistemaslucar/cuponMeli.git
cd cuponMeli

2. Configurar el Proyecto
   Si no has configurado el proyecto aún, asegúrate de tener las dependencias correctas.
Para Spring Boot, en el archivo pom.xml debe estar incluida 
la dependencia para Spring Web, JDBC y H2. Ejemplo:

xml
Copiar código
<dependencies>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
<groupId>com.h2database</groupId>
<artifactId>h2</artifactId>
<scope>runtime</scope>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-json</artifactId>
</dependency>
</dependencies>
3. Construir y Ejecutar la Aplicación
   Si estás usando Maven, ejecuta el siguiente comando para construir y ejecutar la aplicación:

bash
Copiar código
mvn clean spring-boot:run
Si estás usando Gradle, ejecuta:

bash
Copiar código
./gradlew bootRun
Esto iniciará la aplicación Spring Boot. La API estará disponible en el puerto 8080 por defecto.

4. Verificar la API
   Una vez que la aplicación esté en ejecución, puedes verificar la funcionalidad 
de la API mediante las siguientes solicitudes HTTP.

4.1 Consultar el precio de un ítem desde la base de datos
Método HTTP: POST
URL: http://localhost:8081/couponMeli/coupon
Ejemplo: Se relaciona en el proyecto el fichero Pruebas coupon.postman_collection.json
de postman con la prueba 


Ejemplo de uso:   
con body:
{
"item_ids": ["MLA1", "MLA2", "MLA3", "MLA4", "MLA5"],
"amount": 500
}
Respuesta esperada:
{
"item_ids": ["MLA1", "MLA2", "MLA4", "MLA5"],
"total": 480
}



5. Interacciones con la API 
 Metodo: PriceService.getItemprice: Obtener precio desde la API externa de Mercado Libre:
Endpoint: /items/{itemId}
Descripción: Obtiene el precio de un ítem haciendo una solicitud a la API de Mercado Libre.
Ejemplo de uso: GET https://api.mercadolibre.com/items/MLA1
Respuesta esperada: 130.0 (precio de Mercado Libre, dependiendo del ítem)

6. Configuración del application.properties
Si deseas cambiar la configuración del puerto puedes hacerlo en el archivo 
src/main/resources/application.properties.
Algunos ejemplos de configuración son:

properties:

# Configuración del puerto
server.port=8081


7. Ejemplo de Respuesta
   Solicitud: POST http://localhost:8080/price/MLA1
   Respuesta:
   json
   Copiar código
   {
   "price": 130.0
   }
8. Errores Comunes
   Error 404 - Not Found: Si un ítem no se encuentra ni en la base de datos ni en la API de Mercado Libre, la respuesta será un error 404.

Error 500 - Internal Server Error: Si ocurre un problema al realizar la consulta a la base de datos o la API de Mercado Libre, se retornará un error 500.

9. Pruebas Unitarias
   Para este ejercicio no se realizaron pero normalmente se emplean:  JUnit y Mockito para simular las respuestas de la API de Mercado Libre y la base de datos H2. 
   Para ejecutar las pruebas, puedes usar Maven:

   bash
   Copiar código
   mvn test
10. Parar la Aplicación
    Para detener la aplicación, presiona Ctrl+C en la terminal donde se está ejecutando el servidor.

Conclusión
Con esta configuración y los pasos descritos, deberías poder ejecutar y
probar la API de cupones en tu entorno local. Si tienes problemas adicionales, 
asegúrate de revisar los logs de la aplicación para obtener más detalles sobre cualquier 
error que pueda haber ocurrido.

