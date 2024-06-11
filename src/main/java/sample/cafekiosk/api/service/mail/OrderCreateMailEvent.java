package sample.cafekiosk.api.service.mail;

import sample.cafekiosk.api.service.order.response.OrderResponse;

public record OrderCreateMailEvent (
        OrderResponse orderResponse
)

{
}
