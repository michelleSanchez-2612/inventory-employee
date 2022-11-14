# inventory-employee
Inventario de Empleados y su Vacunación

PASOS PARA EJECUTAR LA APLICACIÓN:
1. Antes de Iniciar, se debe crear la base de datos en Postgres.
  CREATE DATABASE inventory_employee;
  
2. Abrir el proyecto y Ejecutar el archivo EmployeeApplication.

3. Ingresar a la url: http://localhost:8080/swagger-ui.html


INFORMACIÓN PARA EJECUTAR LAS API:
-El modelo de datos se sube al repositorio.
-Se muestra ejemplos de los json usados para ejecutar las API.

/**********************ADMIN***************************/
----CREACION DE EMPLEADO-------
{
  "identification":"1727276394",
  "names": "Michelle Elizabeth",
  "lastNames": "Sanchez Puma",
  "email": "mail@mail.com"
}

----ACTUALIZACION DE EMPLEADO-------
{
  "identification":"1727276394",
  "names": "Michelle Elizabeth",
  "lastNames": "Sanchez Puma",
  "email": "usuario@mail.com"
}

/**********************EMPLEADO************************/
----ACTUALIZACION OTRA INFORMACION
{  
  "identification": "1727276394",
  "names": "Michelle Elizabeth",
  "lastNames": "Sanchez Puma",
  "email": "mail@mail.com",
  "birthdate": "1997-12-02",
  "direction": "Quito",
  "phone": "0981447336",
  "vaccinationState": "Vacunado"
}
----CREACION DE VACUNA-------
{
  "vaccinationDate": "2022-01-01",
  "vaccinationDose": 2,
  "vaccineType": "Sputnik"
}
----ACTUALZIACION DE VACUNA-------
{
  "vaccinationDate": "2022-01-01",
  "vaccinationDose": 1,
  "vaccineType": "Sputnik"
}
