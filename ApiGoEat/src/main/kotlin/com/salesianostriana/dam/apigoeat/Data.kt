package com.salesianostriana.dam.apigoeat

import com.salesianostriana.dam.apigoeat.models.*
import com.salesianostriana.dam.apigoeat.repos.*
import com.salesianostriana.dam.apigoeat.services.BarService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import javax.annotation.PostConstruct

@Component
class Data(
        val userRepository: UserRepository,
        val barRepository: BarRepository,
        val platoRepository: PlatoRepository,
        val pedidoRepository: PedidoRepository,
        val lineaPedidoRepository: LineaPedidoRepository,
        val barService: BarService,
        private val encoder: PasswordEncoder
) {
    @PostConstruct
    fun initData() {

        //Bares
        val bares = listOf(
                Bar(
                        "Goiko Grill", "HAMBURGUESAS", "https://www.goiko.com/wp-content/uploads/2017/12/JL171223GOIKO-7.jpg",
                        37.389670, -5.995405, LocalTime.of(8, 10), LocalTime.of(23, 50), 20
                ),
                Bar(
                        "No Piqui", "HAMBURGUESAS", "https://cenados.com/wp-content/uploads/2017/05/fachada-no-piqui-min.jpg",
                        37.400541, -5.993118, LocalTime.of(10, 0), LocalTime.of(23, 30), 15
                ),
                Bar(
                        "Masakali", "PIZZAS", "https://www.srperro.com/media/negocio/7d6cc913-3aa5-47fb-a2b2-f25359f96903.original.jpeg",
                        37.392726, -5.989546, LocalTime.of(9, 0), LocalTime.of(23, 45), 11
                )
        )
        barRepository.saveAll(bares)

        barService.cargarHorarios()

        val platosGoiko = listOf(
                Plato("Classic Burger", "https://www.goiko.com/wp-content/uploads/2017/03/Yankee_Web_Desktop-340x340.jpg", 11.9, "Clasic: salsa 50, queso cheddar, bacon crujiente, tomate, lechuga batavia", "HAMBURGUESAS", bares[0]),
                Plato("Chiken Tenders", "https://www.goiko.com/wp-content/uploads/2017/02/Tenders_Web_Desktop-1.jpg", 9.90, "Hay cosas que nunca fallan. Tiras de pollo empanadas, acompañadas de tu salsa favorita de la casa.", "ENTRANTES", bares[0]),
                Plato("Aros de Cebolla", "https://www.goiko.com/wp-content/uploads/2017/02/Aros_Web_Desktop.jpg", 6.90, "Acompañados de salsa Barbacoa Goiko.", "ENTRANTES", bares[0]),
                Plato("Nachos de Daniela", "https://www.goiko.com/wp-content/uploads/2017/02/Nachos_Web_Desktop2-1.jpg", 11.70, "Un clásico para compartir, ¡el último tiene que ser tuyo! Nachos bañados en chili con carne, queso fundido, guacamole, tomate, nata agria y jalapeños.", "ENTRANTES", bares[0]),
                Plato("Yankee", "https://www.goiko.com/wp-content/uploads/2017/03/Yankee_Web_Desktop.jpg", 15.00, "Para los que le gusta poner toooda la carne en el asador. Carne de vaca, costilla de cerdo en salsa Barbacoa Goiko, queso americano, cebolla al grill y lechuga batavia.", "HAMBURGUESAS", bares[0]),
                Plato("Kevin Bacon", "https://www.goiko.com/wp-content/uploads/2017/03/Kevin_Web_Desktop-340x340.jpg", 11.90, "El auténtico best seller de esta casa. ¿Sabes cómo se hace una Kevin? Fácil pero increíblemente rico: Picamos la carne directamente en la plancha y la mezclamos con trozos de bacon, cebolla crunchy y queso americano", "HAMBURGUESAS", bares[0]),
                Plato("Baby Yankee", "https://www.goiko.com/wp-content/uploads/2017/02/BabyYankee_Web_Desktop-340x340.jpg", 12.50, "Como su propio nombre indica, es la versión baby de La Yankee. Costillas de cerdo deshuesadas en salsa Barbacoa Goiko, queso americano, cebolla a la plancha y lechuga batavia", "HAMBURGUESAS", bares[0]),
                Plato("La Montesa", "https://www.goiko.com/wp-content/uploads/2019/11/MADBO_Web_Desktop-340x340.jpg", 12.50, "Queso Gouda, chunks de pollo frito, salsa César, queso parmesano y lechuga iceberg.", "HAMBURGUESAS ESPECIALES", bares[0]),
                Plato("Chiliraptor", "https://www.goiko.com/wp-content/uploads/2018/06/Chiliraptor_Web_Desktop-340x340.jpg", 10.90, "Para esos días de hambre jurásica. Carne mezclada con chili (sin picante), queso americano, queso Monterey Jack y guacamole. Te recomendamos que la pruebes con bacon bits o chips de nachos, ¡qué rica!", "HAMBURGUESAS ESPECIALES", bares[0]),
                Plato("La San Mateo", "https://www.goiko.com/wp-content/uploads/2019/09/LOGPO_Web_Desktop.jpg", 12.50, "Mayo vino, tuétano, queso Gouda, cebolla grill, chips de vegetales y espinaca", "HAMBURGUESAS ESPECIALES", bares[0]),
                Plato("La Torita", "https://www.goiko.com/wp-content/uploads/2020/01/MATRI_LaTorita.jpg", 12.50, "Queso gouda, rabo de toro, tiras de piquillo, cebolla crunchy y lechuga batavia.", "HAMBURGUESAS ESPECIALES", bares[0]),
                Plato("La Miss", "https://www.goiko.com/wp-content/uploads/2020/01/MADNI_LaMiss-1.jpg", 12.50, "Croqueta de queso crema, cebolleta y alcachofa empanada, dos tiras de bacon, brotes de lombarda y canónigos.", "HAMBURGUESAS ESPECIALES", bares[0]),
                Plato("La Agur", "https://www.goiko.com/wp-content/uploads/2018/11/VITOZ_Web_Desktop.jpg", 12.50, "Queso Ardiona, bacon, setas salteadas, lechuga morada, huevo y mayo Goiko", "HAMBURGUESAS ESPECIALES", bares[0]),
                Plato("Daiquirí", "https://www.goiko.com/wp-content/uploads/2018/02/Daiquiri_Web_Desktop.jpg", 7.50, "¿Solo dos ingredientes para tanto sabor? Efectivamente. Ron blanco y zumo de fresa frozen.", "Bebidas", bares[0]),
                Plato("Goiko Shake", "https://www.goiko.com/wp-content/uploads/2017/02/GoikoShake_Web_Desktop.jpg", 5.50, "Quizá aún no lo sabes, pero quieres uno. Batido de helado de vainilla con galleta y sirope de chocolate, servido con nata montada.", "Bebidas", bares[0]),
                Plato("Mojito Moreno", "https://www.goiko.com/wp-content/uploads/2019/03/Mojito_Web_Desktop.jpg", 7.50, "Ron Santa Teresa, lima, hierbabuena, azúcar moreno y refresco de limón. Aviso: es extremadamente refrescante.", "Bebidas", bares[0]),
                Plato("Goikopita®", "https://www.goiko.com/wp-content/uploads/2017/02/Goikopita_Web_Desktop.jpg", 5.90, "Cóctel con Ron Santa Teresa, zumo de naranja, licor de plátano y sirope de jengibre y guindilla. ¡Una de esas cosas que siempre apetecen!", "Bebidas", bares[0]),
                Plato("Goiko Cookie", "https://www.goiko.com/wp-content/uploads/2019/11/POSTRESPortada_Web_Desktop-3.jpg", 6.50, "El trío perfecto se llama Goiko Cookie: tarta de brownie con cookie y centro de lava de chocolate, servido caliente con una bola de helado de vainilla.", "POSTRES", bares[0]),
                Plato("Carrot Cake", "https://www.goiko.com/wp-content/uploads/2017/02/Carrot_Web.jpg", 5.50, "Deliciosa tarta de zanahoria casera ahora con mucho frosting. Porque… ¿quién quiere una vida sin frosting?", "POSTRES", bares[0]),
                Plato("Cremoso", "https://www.goiko.com/wp-content/uploads/2019/06/Cremoso_Web.jpg", 6.50, "Coulis de frutos rojos con crema de queso. ¡Simple y delicioso!", "POSTRES", bares[0]),
                Plato("Frozen Goiko®", "https://www.goiko.com/wp-content/uploads/2017/02/Frozen_Web-1.jpg", 4.90, "Exquisito secreto de Goiko a base de galleta de chocolate. Como es secreto, lo único que te contaremos es que una vez que lo pruebas… no puedes dejar de pensar en él.", "POSTRES", bares[0]),
                Plato("Chunky Doughy® 2.0", "https://www.goiko.com/wp-content/uploads/2017/02/Chunky_Web.jpg", 6.50, "SPOILER: No has probado nada como esto. Chunks de brownie, helado de vainilla, sirope de salted caramel y black cookie dough.", "POSTRES", bares[0])
        )

        val platosNoPiqui = listOf(
                Plato("Ibérica", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/kysjzwln89sadgcqfhfk", 9.95, "Hamburguesa de buey (200g) en pan brioche, cubierta con delicioso queso y acompañada con auténtica salsa al whisky, jamón y confitura de tomate", "HAMBURGUESAS", bares[1]),
                Plato("American Bourbon", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/cguiqmcirelz8c9bfc3k", 9.95, "200 g de hamburguesa de buey en pan brioche, sobre una cama de tomate y rúcula, cubierta con quso fundido, beicon, aritos de cebolla y coronada con la genuina salsa Bourbon\"> 200 g de hamburguesa de buey en pan brioche, sobre una cama de tomate y rúcu…", "HAMBURGUESAS", bares[1]),
                Plato("Cabrita", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/w6sun5mncgfevbpnyn9b", 9.95, "200 gramos de carne de vacuno en pan brioche sobre frescas hojas de rucula, y rematados la hamburguesa con medallones de queso de cabra y sabrosa cebolla caramelizada.", "HAMBURGUESAS", bares[1]),
                Plato("Chimiburguer", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/zm3k4xyk6j3obowuakmj", 9.95, "200 gramos de carne de vacuno en pan brioche,  sobre frescas hojas de rucula, cubierto por extraordinario queso provolone fundido y culminando por sabrosa salsa chimichurri", "HAMBURGUESAS", bares[1]),
                Plato("Clásica", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/gxok3dw6vonmuu8fliki", 9.95, "Hamburguesa de buey (200g) en pan brioche con lechuga, tomate cebolla, pepinillos y queso fundido", "HAMBURGUESAS", bares[1]),
                Plato("Chipottle Cheese Fries", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/noepo3h2kpsvnqzrymdl", 7.95, "Crujientes patatas fritas acompañadas de aútentico pollo chipottle, cubiertas con salsa Tex Mex y abundante queso fundido", "ENTRANTES", bares[1]),
                Plato("Chicken Cheese Nuggets", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/wxitcnmdfajb7ufh42yq", 9.95, "Deliciosos y crujientes nuggets rellenos de pollo y acompañados de salsa", "ENTRANTES", bares[1]),
                Plato("Alitas Barbacoa", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/zkjypniak5fatvivrnh6", 9.95, "Alitas de pollo recubiertos con una fina y deliciosa capa de barbacoa", "ENTRANTES", bares[1]),
                Plato("Agua Mineral", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/5d4282871843404e8e35881a070b1da8.jpg", 1.50, "Botella de 50cl", "BEBIDAS", bares[1]),
                Plato("Coca Cola", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/51fb2c7b95ed4ae1bf0f34fa7898341c.jpg", 2.00, "Lata de 33cl", "BEBIDAS", bares[1]),
                Plato("Coca Cola Cero", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/729551dd91f34783bce6e47b164f5013.jpg", 2.00, "Lata de 33cl", "BEBIDAS", bares[1]),
                Plato("Fanta De Naranja", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/3157768db5ec4a3aa3ba8ab018ac1b93.jpg", 2.00, "Lata de 33cl", "BEBIDAS", bares[1]),
                Plato("Red Bull", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/e8e9fa21930a466998def230d2d7ce95.jpg", 3.20, "", "BEBIDAS", bares[1]),
                Plato("Red Bull SUGARFREE", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/45a03d41df544c61974a9b397573680a.jpg", 3.20, "", "BEBIDAS", bares[1]),
                Plato("Red Bull AÇAÍ", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/008c9f09b3e546b99d5f6cd13cbdd99b.jpg", 3.00, "", "BEBIDAS", bares[1]),
                Plato("Ensalada Quinoa", "https://www.diariodesevilla.es/2018/08/27/con_cuchillo_y_tenedor/cosas_de_come/ensalada-quinoa_1276682866_88378772_1821x1024.jpg", 10.90, "Mix verde, pollo plancha, manzana, quinoa, piñones, aguacate y mango y vinagreta balsámica", "ENSALADAS", bares[1]),
                Plato("Ensalada No Piqui", "https://media-cdn.tripadvisor.com/media/photo-s/13/d0/1a/7a/ensalada-en-no-piqui.jpg", 10.90, "Canónigos, mix de lechuga, pollo empanado, parmesano, manzana, aguacate, nueces, vinagreta de mostaza y miel", "ENSALADAS", bares[1]),
                Plato("Ensalada Caprese Burratta", "https://res.cloudinary.com/glovoapp/w_680,h_240,c_fit,f_auto,q_auto/Products/fmqwpmlc1m15zf5umdfg", 10.90, "Selección de tomates de temporada, rúcula, burrata de búfala, reducción de vinagre de módena", "ENSALADAS", bares[1])
        )

        val platosMasakali = listOf(
                Plato("Pizza Duquesa", "https://d1ralsognjng37.cloudfront.net/a9d6be96-cc5c-441d-a4cf-dede85607806.jpeg", 9.00, "Pollo, calabacín, cebolla morada y orégano. Base de tomate y mozzarella. Nuestras PIZZAS están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "PIZZAS ESPECIALES", bares[2]),
                Plato("Pizza York", "https://d1ralsognjng37.cloudfront.net/b405ec58-7b3a-4bb3-8ef2-7935d65c3b5f.jpeg", 8.00, "Base de tomate y mozzarella. Nuestras PIZZAS están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "PIZZAS TRADICIONALES", bares[2]),
                Plato("Pizza Margarita con Orégano", "https://d1ralsognjng37.cloudfront.net/186504f9-f4bc-4715-9bd1-5ab7fc8c67f3.jpeg", 8.00, "Base de tomate y mozzarella. Nuestras PIZZAS están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "PIZZAS TRADICIONALES", bares[2]),
                Plato("Pizza Serranito Alioli", "https://d1ralsognjng37.cloudfront.net/e50b35df-12c1-4bdd-81a4-5747213b4959.jpeg", 9.00, "Pollo, jamón serrano, pimiento verde y alioli casero. Base de tomate y mozzarella. Nuestras PIZZAS están hechas con masa madre y aceite de oliva virgen extra. Contiene: Lactosa y gluten.", "PIZZAS ESPECIALES", bares[2]),
                Plato("Pizza Vegetal", "https://d1ralsognjng37.cloudfront.net/d88d9721-8fab-424b-92ad-6dc9068e42cb.jpeg", 9.00, "Berenjena, calabacín, pimiento rojo y orégano. Base de tomate y mozzarella. Nuestras PIZZAS están hechas con masa madre y aceite de oliva virgen extra. Contiene: Gluten.", "PIZZAS ESPECIALES", bares[2]),
                Plato("Pizza Cinco Quesos", "https://d1ralsognjng37.cloudfront.net/0e11cc5c-f54f-415f-84fa-cddcfb76db1f.jpeg", 9.00, "Mozzarella, cheddar, queso azul, cabra, grana padano y orégano. Base de tomate y mozzarella. Nuestras PIZZAS están hechas con masa madre y aceite de oliva virgen extra. Contiene: Lactosa, gluten y huevo.", "PIZZAS ESPECIALES", bares[2]),
                Plato("Lata Coca Cola", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/51fb2c7b95ed4ae1bf0f34fa7898341c.jpg", 1.40, "33cl.", "BEBIDAS", bares[2]),
                Plato("Lata Coca Cola Light", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/6315c436ea844951b7021584381d31d8.jpg", 1.40, "33cl.", "BEBIDAS", bares[2]),
                Plato("Lata Coca Cola Zero", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/729551dd91f34783bce6e47b164f5013.jpg", 1.40, "33cl.", "BEBIDAS", bares[2]),
                Plato("Fanta de naranja", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/3157768db5ec4a3aa3ba8ab018ac1b93.jpg", 1.40, "500ml", "BEBIDAS", bares[2]),
                Plato("Nestea", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/f889dd14539b46aeb486b5664464866d.jpg", 1.40, "500ml", "BEBIDAS", bares[2]),
                Plato("Cerveza KVN", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/b19c50f80c0d4ddaab26f1078ffa1de3.jpg", 3.40, "33cl.", "BEBIDAS", bares[2]),
                Plato("Alhambra Reserva 1925", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/a49580ca93974661824fe5c2cc49089d.jpg", 3.90, "33cl.", "BEBIDAS", bares[2]),
                Plato("Mahou Maestra", "http://euw1.posios.com/posimages/ggmmls@goikogrill.com_36172/images/products/539f8bf75cb54b2dbb800b694fa36451.jpg", 3.90, "33cl.", "BEBIDAS", bares[2]),
                Plato("Postre de galleta con chocolate blanco", "https://d1ralsognjng37.cloudfront.net/f8a8906a-877a-4b83-afc4-f89990204884.jpeg", 4.50, "Tarta de chocolate blanco con galleta", "POSTRES", bares[2]),
                Plato("Postre de Kinder", "https://d1ralsognjng37.cloudfront.net/256b12cf-e13a-4131-98cf-09d122e295b0.jpeg", 8.00, "Tarta de galleta kinder", "POSTRES", bares[2]),
                Plato("Postre de 3 Chocolates Belgas", "https://d1ralsognjng37.cloudfront.net/4a744281-ed8c-44eb-8dfa-a5e20087a95b.jpeg", 4.50, "Tarta casera", "POSTRES", bares[2])
        )

        platoRepository.saveAll(platosGoiko)
        platoRepository.saveAll(platosNoPiqui)
        platoRepository.saveAll(platosMasakali)

        //Usuarios
        val usuarios = listOf(
                User("goiko@gmail.com", "goiko","Administrador Goiko Grill", encoder.encode("123456"), "https://pbs.twimg.com/profile_images/1153966095444992000/1lpIyHaQ.jpg", mutableSetOf("ADMIN"), LocalDate.of(2019, 7, 13), null, bares[0]),
                User("paco@gmail.com", "Paco","Paco Alcántara", encoder.encode("123456"), "https://randomuser.me/api/portraits/men/4.jpg", mutableSetOf("USER"), LocalDate.of(2020, 2, 21)),
                User("juanito@gmail.com", "Juan Carlos","Juan Carlos López", encoder.encode("123456"), "https://randomuser.me/api/portraits/men/32.jpg", mutableSetOf("USER"), LocalDate.of(2019, 12, 24)),
                User("bosco@gmail.com", "Laura","Paco Alcántara", encoder.encode("123456"), "https://randomuser.me/api/portraits/women/86.jpg", mutableSetOf("USER"), LocalDate.of(2020, 5, 7))
        )
        userRepository.saveAll(usuarios)

        val pedidos = listOf(
                Pedido(LocalDate.of(2020, 3, 5), 79.5, Estado.ENTREGADO, true, LocalTime.of(20, 10), usuarios[0], bares[0]),
                Pedido(LocalDate.of(2020, 5, 27), 26.0, Estado.ENTREGADO, false, LocalTime.of(20, 55), usuarios[2], bares[2]),
                Pedido(LocalDate.of(2020, 5, 23), 29.85, Estado.ENTREGADO, false, LocalTime.of(18, 15), usuarios[3], bares[1]),
                Pedido(LocalDate.of(2020, 3, 14), 19.8, Estado.ENTREGADO, true, LocalTime.of(19, 5), usuarios[3], bares[2]),
                Pedido(LocalDate.of(2020, 5, 23), 34.75, Estado.ENTREGADO, false, LocalTime.of(20, 45), usuarios[2], bares[1]),
                Pedido(LocalDate.now(), 48.1, Estado.PREPARADO, false, LocalTime.of(14, 30), usuarios[0], bares[0], "La hamburguesa muy hecha por favor"),
                Pedido(LocalDate.now(), 48.4, Estado.COCINA, false, LocalTime.of(12, 10), usuarios[2], bares[0]),
                Pedido(LocalDate.now(), 50.0, Estado.SOLICITADO, false, LocalTime.of(20, 50), usuarios[3], bares[0])

        )
        pedidoRepository.saveAll(pedidos)

        val lineas = listOf(
                //Pedido 0
                LineaPedido(3, 37.5, platosGoiko[7], pedidos[0]),
                LineaPedido(3, 22.5, platosGoiko[13], pedidos[0]),
                LineaPedido(3, 19.5, platosGoiko[17], pedidos[0]),

                //Pedido 1
                LineaPedido(1,8.0, platosMasakali[2], pedidos[1]),
                LineaPedido(2,18.0, platosMasakali[4], pedidos[1]),

                //Pedido 2
                LineaPedido(1,9.95, platosNoPiqui[2], pedidos[2]),
                LineaPedido(2,19.9, platosNoPiqui[4], pedidos[2]),

                //Pedido 3
                LineaPedido(1,8.0, platosMasakali[1], pedidos[3]),
                LineaPedido(1,9.0, platosMasakali[5], pedidos[3]),
                LineaPedido(2,2.8, platosMasakali[6], pedidos[3]),

                //Pedido 4
                LineaPedido(1,9.95, platosNoPiqui[6], pedidos[4]),
                LineaPedido(2,21.8, platosNoPiqui[16], pedidos[4]),
                LineaPedido(2,3.0, platosNoPiqui[8], pedidos[4]),

                //Pedido 5
                LineaPedido(2, 23.8, platosGoiko[0], pedidos[5]),
                LineaPedido(1, 6.9, platosGoiko[2], pedidos[5]),
                LineaPedido(1, 9.9, platosGoiko[1], pedidos[5]),
                LineaPedido(1, 7.5, platosGoiko[19], pedidos[5]),

                //Pedido 6
                LineaPedido(1, 7.5, platosGoiko[15], pedidos[6]),
                LineaPedido(1, 12.5, platosGoiko[6], pedidos[6]),
                LineaPedido(1, 10.9, platosGoiko[8], pedidos[6]),
                LineaPedido(1, 6.5, platosGoiko[17], pedidos[6]),
                LineaPedido(2, 11.0, platosGoiko[18], pedidos[6]),

                //Pedido 7
                LineaPedido(2, 25.0, platosGoiko[11], pedidos[7]),
                LineaPedido(1, 5.5, platosGoiko[14], pedidos[7]),
                LineaPedido(3, 19.5, platosGoiko[19], pedidos[7])
        )

        lineaPedidoRepository.saveAll(lineas)
    }
}