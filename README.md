# Coffee Shop API

## Generate JAR
```shell
gradle build
```

## Build Docker image
```shell
docker build -t coffeeshop-api:latest
```

## Run
```shell
docker-compose up
```

## Endpoints
```
POST    /users/{userId}/carts 
POST    /users/{userId}/carts/{cartId}
PATCH   /users/{userId}/carts/{cartId}/products/{productId}
POST    /users/{userId}/carts/{cartId}/orders
```