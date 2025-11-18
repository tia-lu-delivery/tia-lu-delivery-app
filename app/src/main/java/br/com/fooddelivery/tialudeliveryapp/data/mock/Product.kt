package br.com.fooddelivery.tialudeliveryapp.data.mock

import br.com.fooddelivery.tialudeliveryapp.data.model.Product

val products: List<Product> = listOf(
    Product(
        productId = "P501",
        name = "X-Burger Clássico",
        descripton = "Hambúrguer de 180g, queijo cheddar, alface, tomate e maionese especial no pão brioche.",
        price = 25.90,
        quantity = 50,
        imageUrl = "https://blog.biglar.com.br/wp-content/uploads/2024/08/iStock-1398630614.jpg",
        isAvailable = true
    ),
    Product(
        productId = "P510",
        name = "Coca-Cola 2L",
        descripton = "Refrigerante de Cola.",
        price = 5.90,
        quantity = 100,
        imageUrl = "https://blog.biglar.com.br/wp-content/uploads/2024/08/iStock-1398630614.jpg",
        isAvailable = true
    )
)