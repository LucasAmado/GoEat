# GoEat
 
App está pensada para ayudar a vender comida a domicilio a los pequeños bares que hasta ahora no lo hacían. Su principal punto fuerte es que cualquiera de ellos podrá gestionar de manera personalizada cuánto tiempo tardan en preparar una comanda. 
En función del tiempo marcado y los horarios de cierre y apertura se calcularán de forma automática las horas a la que los clientes pueden acercarse a recoger su pedido, evitando así el colapso en las cocinas y las largas colas de espera.
 
## Usuarios
```
USUARIO
    username: Paco
    password: 123456
 
ADMIN
    username: goiko
    password: 123456
```
 
## __Funcionalidades__ 
 
## Cálculo de horas de recogida
Al arrancar la api se recorren los bares para calcular las horas de recogida de los pedidos de cada uno. Todo ello en base a la hora de apertura, cierre y cuánto tiempo se tarde en elaborar un pedido.
 
Gracias a esto si en un futuro algún bar cambiase de horarios o el tiempo requerido por encargo se podrían cambiar las horas de recogida de cada bar.
 
## Registro
Al registrarse el usuario deberá indicar nombre de usuario, email, contraseña y confirmación de contraseña.
 
Soy consciente de que debería haber una petición multiparte pero no conseguía hacerlo y decidí seguir avanzando para no quedarme atascado, pero sé que lo tengo pendiente.
 
## Login
Parar iniciar sesión será necesario el nombre de usuario y la contraseña. Una vez logeado debemos diferenciar entre administradores y usuarios.
 
## Mi perfil
Desde esta opción el usuario podrá modificar sus datos como puede ser el nombre de usuario, avatar o email.
 
 
* USUARIOS
 
    * ## Lista de bares
        En la página principal encontraremos los bares que estén abiertos. Entre ellos se podrá filtrar entre aquellos que se consideren los favoritos, además de por el tiempo de comida.
 
    * ## Detalle bar
        Al seleccionar un bar podremos acceder a sus datos. Entre ellos el nombre, horario, imagen y una carta en la que aparecerán los platos filtrados por categoría.
 
        Adicionalmente se podrá ver su locación concreta en google maps
 
    * ## Lista de platos
        Una vez selecciona la categoría (bebidas, postres, etc) accederemos a una lista de platos en la que se mostrará el nombre, precio unitario e imagen
 
    * ## Detalle plato
        Al seleccionar un plato accederemos a todos sus datos como son además de los mencionados anteriormente la descripción.
        Desde aquí podremos añadir x cantidad o borrarla en caso de estuviera en el carrito.
 
    * ## Carrito
        Desde aquí tendremos acceso a todos los platos que se hayan ido añadiendo y el precio total.
        
        Para poder pagar será necesario indicar la hora de recogida y si se desea se podrá dejar algún comentario para que los cocineros lo tengan presente.
        Si el pago se realiza de manera correcta se accederá instantáneamente a su detalle
 
    * ## Mis pedidos
        Desde el segundo botón del menú se podrá acceder a un histórico
        de los pedidos realizados ordenados cronológicamente.
 
        Se contará con la opción de filtrar por "mis pedidos favoritos"
 
    * ## Detalle pedido
        Al elegir un pedido se podrá acceder a toda su información, pudiendo marcarlo como favorito.
 
* ADMINISTRADORES
    * ## Lista de pedidos
        La página principal consta de una lista de todos los pedidos que se esperan para hoy. En cada uno incluirá la hora de recogida y el estado (pedido, preparándose, listo y recogido).
 
        Si se desea se podrá acceder a un histórico de pedidos.
 
    * ## Gestión del bar
        Desde el segundo botón de navegación se podrán modificar los datos del bar que se deseen como pueden ser las horas de apertura y cierre, el tiempo necesario en elaborar cada encargo o el nombre.



Adjunto [colección de postman](https://www.getpostman.com/collections/7eada23f41faaa658c2b) por si os pudiera facilitar las pruebas