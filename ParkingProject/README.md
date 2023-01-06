
##Programa de gestion del Parking

Este programa realiza la gestion del parking de Deusto y es usado tanto 
por alumnos como profesores para dejar sus vehiculos cuando llegan a la
universidad. Este programa a su vez esta dividido en dos secciones: por un lado,
la referente a los clientes del parking, es decir, alumnos y profesores, y por otro lado,
la que trata la gestion por parte de los trabajadores de este parking. 

En la seccion de clientes, estos se dividiran entre clientes ordinarios, de un
solo uso del parking, y clientes abonados de uso multiple. A su vez, estos se
volveran a clasificar segun el vehiculo que dispongan, y se les ofertara distintas
tarifas por ello.

En cuanto a la seccion de trabajadores, estos se dividiran en empleados y manager.
Las diferencias entre ambos dependen de las funciones que realizan en el programa,
es decir, cada cual realizara sus respectivas tareas.

##Instalacion del proyecto

Previo a la instalacion del proyecto, debemos asegurarnos de tener instalado
el JDK en nuestro equipo. En caso de no tenerlo, puede descargarlo a traves de la 
siguiente direccion:

https://www.oracle.com/java/technologies/javase-downloads.html

Una vez tenga instalado el JDK, realice los siguientes pasos para instalar el proyecto
a partir del archivo .jar del proyecto:

1. Descarga el archivo .jar del proyecto.
2. Abre Eclipse y selecciona la opción "Import" del menú "File".
3. En la ventana de "Import", selecciona la opción "Import existing projects into workspace" 
   y haz clic en "Next".
4. En la siguiente pantalla, haz clic en el botón "Browse" y navega hasta la ubicación 
   donde hayas guardado el archivo .jar del programa. Selecciónalo y haz clic en "Open".
5. Verifica que el proyecto aparezca en la lista de proyectos disponibles 
   para importar y haz clic en "Finish".

##Uso del programa

En el inicio de programa, se da la opcion de acceder al area de clientes o 
area de trabajadores. A continuacion, describiremos el uso del area de clientes.

Area de clientes.

En primer lugar, se pide al cliente que ingrese su matricula, y accedera al 
panel de bienvenida del parking. Se pedira al cliente que escoja una plaza para 
acceder al mismo. Para ello, se deben de seguir los siguientes pasos:

1. Debe seleccionar que tipo de plaza va a ocupar en funcion de la clase de vehiculo
   que tenga.
2. En funcion de su interes en la estancia del parking, puede elegir acceder al parking
   en un solo uso o por varios usos mediante la compra de un bono. Esta opcion la 
   puede escoger pulsando en los respectivos botones. 
3. Si ha accedido a una sola estancia en el parking con el boton "acceder", 
   debe seleccionar una plaza libre en una planta. Para ello, debe seleccionar
   cualquiera de las plantas ofrecidas en el panel, y seleccionar una plaza disponible,
   que estan representadas en color verde. Para finalizar este proceso, debe darle
   a "aceptar" y su plaza estará seleccionada.
4. En caso de seleccionar el boton "Comprar abono", el proceso sera similar al anterior.
   Debe seleccionar una plaza que esta disponible, y posteriormente, debe darle a "Comprar"
   segun el tipo de abono que quiere comprar. Le aparecera una ventana para poder pagar, 
   para ello debe seleccionar "pagar" y confirmar su transaccion.
5. Si en alguno de estos casos no quiere seguir con una de las opciones, puede darle al
   boton "Cancelar".

Si ya ha accedido al parking, el siguiente ingreso de matricula en el inicio sera
para usos distintos segun el tipo de cliente. En caso de ser un cliente ordinario,
le aparecera un panel de pago para poder salir del parking. Para pagar, debe seleccionar
"pagar" y debe confirmar la transaccion.

En caso de ser un cliende subscrito a un abono, le aparecera una imagen de su estacionamiento
con el fin de que pueda acceder facilmente a el.

Finalmente, el programa se cerrara automaticamente o le daran la opcion de "Salir".

Area de trabajadores

Para entrar como trabajador en este programa, debe ingresar sus credenciales con su clave y
pulsar en "Continuar". 

En caso de no recordar su clave, debe hacer click en "Recordar credenciales".
Se abrira un nuevo panel que le pedira su nombre de usuario y DNI para recuperar las credenciales.
Tras haber realizado el paso anterior, debe seleccionar "Recuperar credenciales" y le llegara
a su e-mail una nueva clave para poder acceder al programa. 

Si ha podido continuar, en el panel de bienvenida dispone de varias funciones que puede realizar:

1. Pulsando en "Datos personales" aparecera un nuevo panel que aparecen los mismos. 
2. Si debe eliminar abonos, debe seleccionar "anular abonados" para realizar esa funcion. 
   Como puede comprobar, se ha abierto un nuevo panel con la lista de todos los clientes subscritos
   actuales en el parking, y apareceran en rojo aquellos cuya fecha ha excedido de la de la caducidad
   del bono. Para dar de baja a un abonado, debe seleccionar dentro de esa lista un abonado y darle a 
   "Dar de baja" para finalmente hacerlo
3. Como trabajador del parking, puede comprobar el estado del parking y realizar las consultas correspondientes
   dando a la opcion "Estado del parking". Si pulsa en consultas, y alguna de sus opciones, apareceran distintos 
   datos de interes para sus consultas. Ademas, en algunos casos podra observar una grafica pulsando en 
   "Observar grafica" en un panel adicional.
4. Si quiere cerrar su sesion como trabajador, puede dar a "Cerrar sesion" para hacerlo, ademas volvera
   al panel de inicio.

En cada una de las opciones, tiene la opcion "Volver" para volver al panel de bienvenida.

##Creditos

Los creadores del programa son estudiantes de la Universidad de Deusto, que estan realizando el primer cuatrimestre
del tercer curso de la doble titulacion de ADE e Ingenieria Informatica en San Sebastian. Los autores son:
- Miguel Aroztegi Tolosa
- Iker Lekuona Aragon
- Eduardo Jorge Sanjurjo Aramburu
