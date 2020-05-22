package com.salesianostriana.dam.apigoeat

import com.salesianostriana.dam.apigoeat.models.Bar
import com.salesianostriana.dam.apigoeat.models.Plato
import com.salesianostriana.dam.apigoeat.models.User
import com.salesianostriana.dam.apigoeat.repos.BarRepository
import com.salesianostriana.dam.apigoeat.repos.PlatoRepository
import com.salesianostriana.dam.apigoeat.repos.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalTime
import javax.annotation.PostConstruct

@Component
class Data(
        val userRepository: UserRepository,
        val barRepository: BarRepository,
        val platoRepository: PlatoRepository,
        private val encoder: PasswordEncoder
) {
    @PostConstruct
    fun initData(){

        //Bares
        val bares = listOf(
                Bar(
                        "Goiko - Albareda", "Hamburguesas",
                        "goiko/logo.jpeg",
                        37.389670,-5.995405,
                        LocalTime.of( 13, 0), LocalTime.of( 23, 30),
                        20, 25
                ),
                Bar(
                        "Masakali Pizza", "Pizza",
                        "malasaki/logo.jpeg",
                        37.392726,-5.989546,
                        LocalTime.of( 13, 0), LocalTime.of( 16, 30),
                        14, 20
                ),
                Bar(
                        "La Piazza", "Pizzas", "piazza/logo.jpeg",37.397413,-5.993683,
                        LocalTime.of( 13, 0), LocalTime.of( 23, 0),
                        8, 15
                )
        )
        barRepository.saveAll(bares)

        val platos = listOf(
                //Goiko
                Plato("Classic Burger", "goiko/classic.jpeg", 11.9, "Clasic: salsa 50, queso cheddar, bacon crujiente, tomate, lechuga batavia", "Hamburguesas", bares[0]),
                Plato("Chiken Tenders", "goiko/chiken-tenders.jpeg", 9.90, "7 tiras de pollo empanadas, con tu salsa favorita de la casa.", "Entrante", bares[0]),
                Plato("Aros de Cebolla", "goiko/aros-cebolla.jpeg", 6.90, "Acompañados de salsa Barbacoa Goiko.", "Entrante", bares[0]),
                Plato("Kevin Bacon", "goiko/kevin-bacon.jpeg", 11.90, "Carne picada y mezclada con bacon, cebolla crunchy y queso americano.", "Hamburguesas", bares[0]),
                Plato("Kendall Bacon", "goiko/kendall-bacon.jpeg", 12.50, "Carne picada y mezclada con queso de cabra, queso americano, bacon bits y cebolla caramelizada #Kendalltedeseo.", "Hamburguesas", bares[0]),
                Plato("Mexican Beef", "goiko/mexican-beef.jpeg", 12.50, "Carne jugosa, chutney de piña, tomate, pollo mexicano, cebolla, lechuga y enquésame.", "Hamburguesas", bares[0]),
                Plato("Creamy Crunch", "goiko/creamy-crunch.jpeg", 10.90, "Queso crema y cebolla crunchy.", "Hamburguesas", bares[0]),
                Plato("Mahou Maestra", "bebidas/mahou-maestra.jpeg", 3.90, "33cl.", "Bebidas", bares[0]),
                Plato("Alhambra Reserva 1925", "bebidas/alhambra-reserva.jpeg", 3.90, "33cl.", "Bebidas", bares[0]),
                Plato("Agua Mineral", "bebidas/agua.jpeg", 1.50, "50cl", "Bebidas", bares[0]),
                Plato("Coca Cola", "bebidas/cola.jpeg", 2.00, "33cl.", "Bebidas", bares[0]),
                Plato("Coca Cola Light", "bebidas/cola-light.jpeg", 2.00, "33cl.", "Bebidas", bares[0]),
                Plato("Coca Cola Zero", "bebidas/cola-cero.jpeg", 2.00, "33cl.", "Bebidas", bares[0]),
                Plato("Fanta de naranja", "bebidas/fanta.jpeg", 2.00, "33cl.", "Bebidas", bares[0]),

                //Masakali
                Plato("Pizza Duquesa", "masakali/duquesa.jpeg", 9.00, "Pollo, calabacín, cebolla morada y orégano. Base de tomate y mozzarella. Nuestras pizzas están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "Especiales", bares[1]),
                Plato("Pizza York", "masakali/york.jpeg", 8.00, "Base de tomate y mozzarella. Nuestras pizzas están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "Tradicionales", bares[1]),
                Plato("Pizza Margarita con Orégano", "masakali/margarita.jpeg", 8.00, "Base de tomate y mozzarella. Nuestras pizzas están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "Tradicionales", bares[1]),
                Plato("Pizza Serranito Alioli", "masakali/serranito-alioli.jpeg", 9.00, "Pollo, jamón serrano, pimiento verde y alioli casero. Base de tomate y mozzarella. Nuestras pizzas están hechas con masa madre y aceite de oliva virgen extra. Contiene: Lactosa y gluten.", "Especiales", bares[1]),
                Plato("Pizza Vegetal", "masakali/vegetal.jpeg", 9.00, "Berenjena, calabacín, pimiento rojo y orégano. Base de tomate y mozzarella. Nuestras pizzas están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "Especiales", bares[1]),
                Plato("Postre de galleta con chocolate blanco", "masakali/galleta-cholate.jpeg", 4.50, "Tarta de chocolate blanco con galleta", "Postres", bares[1]),
                Plato("Postre de Kinder", "masakali/kinder.jpeg", 8.00, "Tarta de galleta kinder", "Postres", bares[1]),
                Plato("Postre de 3 Chocolates Belgas", "masakali/belga.jpeg", 4.50, "Tarta casera", "Postres", bares[1]),
                Plato("Pizza Cinco Quesos", "masakali/margarita.jpeg", 9.00, "Mozzarella, cheddar, queso azul, cabra, grana padano y orégano. Base de tomate y mozzarella. Nuestras pizzas están hechas con masa madre y aceite de oliva virgen extra. Contiene: Lactosa, gluten y huevo.", "Especiales", bares[1]),
                Plato("Lata Coca Cola", "bebidas/cola.jpeg", 1.40, "33cl.", "Bebidas", bares[1]),
                Plato("Lata Coca Cola Light", "bebidas/cola-light.jpeg", 1.40, "33cl.", "Bebidas", bares[1]),
                Plato("Lata Coca Cola Zero", "bebidas/cola-cero.jpeg", 1.40, "33cl.", "Bebidas", bares[1]),
                Plato("Fanta de naranja", "bebidas/fanta.jpeg", 1.40, "500ml", "Bebidas", bares[1]),
                Plato("Botellín Cruzcampo", "bebidas/cruzcampo.jpeg", 1.40, "33cl.", "Bebidas", bares[1]),
                Plato("Botellín Cruzcampo sin alcohol", "bebidas/cruzcampo-sin.jpeg", 1.40, "33cl.", "Bebidas", bares[1]),

                //Piazza
                Plato("Pizza Barbacoa", "piazza/barbacoa.jpeg", 10.00, "Tomate, mozzarella, carne de pollo, bacon, carne de cerdo y salsa barbacoa.", "Pizzas", bares[2]),
                Plato("Pizza de Pollo", "piazza/pollo.jpeg", 10.65, "Tomate, mozzarella, pollo braseado, jamón york, pimiento y orégano.", "Pizzas", bares[2]),
                Plato("Pizza Carbonara", "piazza/carbonara.jpeg", 10.00, "", "Pizzas", bares[2]),
                Plato("Pizza Pepperoni", "piazza/pepperoni.jpeg", 9.50, "Salsa de tomate, mozzarella y peperoni.", "Pizzas", bares[2]),
                Plato("Pizza 4 Estaciones", "piazza/estaciones.jpeg", 10.00, "Salsa de tomate, mozzarella, bacon, pimientos champiñones y aceitunas.", "Pizzas", bares[2]),
                Plato("Pizza Cabra Green", "piazza/green.jpeg", 10.50, "Salsa de tomate, mozzarella, espinacas, cebolla, queso de cabra, parmesano, nuestra salsa de ajo y perejil.", "Pizzas", bares[2]),
                Plato("Pizza Tonno", "piazza/tonno.jpeg", 10.00, "Salsa de tomate, mozzarella, atún y cebolla.", "Pizzas", bares[2]),
                Plato("Pizza Vegetal", "piazza/vegetal.jpeg", 10.00, "Salsa de tomate, mozzarella, berenjena, cebolla, pimiento, tomate en rodajas, champiñones, nuestra salsa de ajo y perejil.", "Pizzas", bares[2]),
                Plato("Botella de agua pequeña", "bebidas/agua-grande.jpeg", 1.50, "0,5L.", "Bebidas", bares[2]),
                Plato("Botella de agua grande", "bebidas/agua.jpeg", 2.00, "1,5L.", "Bebidas", bares[2]),
                Plato("Coca Cola", "bebidas/cola.jpeg", 1.80, "33cl.", "Bebidas", bares[2]),
                Plato("Coca Cola Light", "bebidas/cola-light.jpeg", 1.80, "33cl.", "Bebidas", bares[2]),
                Plato("Coca Cola Zero", "bebidas/cola-cero.jpeg", 1.80, "33cl.", "Bebidas", bares[2]),
                Plato("7Up", "bebidas/7up.jpeg", 1.80, "33cl.", "Bebidas", bares[2])
        )

        platoRepository.saveAll(platos)

        //Usuarios
        val usuarios = listOf(
                User("goiko", "goiko@gmail.com", encoder.encode("123456"), "", mutableSetOf("ADMIN"), bares[0]),
                User("user", "user@gmail.com", encoder.encode("123456"), "", mutableSetOf("USER"))
        )
        userRepository.saveAll(usuarios)
    }
}