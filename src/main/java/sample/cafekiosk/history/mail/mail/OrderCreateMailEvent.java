package sample.cafekiosk.history.mail.mail;

import sample.cafekiosk.order.response.OrderResponse;

public record OrderCreateMailEvent (
        OrderResponse orderResponse
)

{
}
