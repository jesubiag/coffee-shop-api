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

### Create Cart
```
POST    /users/{userId}/carts 
```
#### Request
_No body_
#### Response
`HTTP 201`
```json
{
  "cartId": "64f51dbccf268864df585391"
}
```

### Add Products
```
POST    /users/{userId}/carts/{cartId}
```
#### Request
```json
{
  "products": [
    {
      "id": "latte_id",
      "name": "Latte",
      "category": "Coffee",
      "price": 5.1,
      "amount": 3
    },
    {
      "id": "mug_id",
      "name": "Mug",
      "category": "Equipment",
      "price": 15,
      "amount": 3
    }
  ]
}
```
#### Response
`HTTP 204`

_No body_

### Modify Quantity
```
PATCH   /users/{userId}/carts/{cartId}/products/{productId}
```
### Request
```json
{
    "amount": 5
}
```
### Response
`HTTP 204`

_No body_

### Create Order
```
POST    /users/{userId}/carts/{cartId}/orders
```
### Request
_No body_
### Response
`HTTP 201`
```json
{
  "cartId": "64f51dbccf268864df585391",
  "totals": {
    "products": 9,
    "discounts": 0.0,
    "shipping": 27.0,
    "order": 97.5
  }
}
```