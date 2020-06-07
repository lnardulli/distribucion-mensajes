# ApiRest para distribuir mensajes.
Esta aplicación se encarga de decidir que proovedor telefonico usar apartir del prefijo telefonico, balanceando la carga y aplicando una estrategia de LCR (Least Cost Routing).
#### ¿Cómo decidí resolverlo?
Decidí utilizar redis para almacenar los proveedores en memoria y que sean de rápido acceso. Para la lógica de qué proovedor utilizar, elegí resolverlo con una priorityQueue ordenando por la cantidad de mensajes enviados. 

### Liberías de terceros utilizadas
 - [org.projectlombok](http://www.dropwizard.io/1.0.2/docs/)
 - [redis.clients](https://redis.io/clients)

##Building
Para realizar el build la máquina deberá tener [instalado docker](https://docs.docker.com/desktop/).

Estando en el root del proyectos.

#### Levantar apiRest en localhost:8080

```bash
docker-compose down
```
```bash
DOCKER_FILE=multistage docker-compose build
```
```bash
docker-compose up
```

#### Ejecutar la "prueba 1" iterando 10 veces con el numero 0034666111222

```bash
docker-compose down
```
```bash
DOCKER_FILE=test-0034 docker-compose build
```
```bash
docker-compose up
```


#### Ejecutar la "prueba 2" iterando 10 veces con el numero 0033777111222
```bash
docker-compose down
```
```bash
DOCKER_FILE=test-0034 docker-compose build
```
```bash
docker-compose up
```

### URL rest
                    
| Url      | Method | Parameters | 
| ------ | ------ | ------ |
| /v1/message/send      |    POST |  ```{"number": string, "message": string}``` |