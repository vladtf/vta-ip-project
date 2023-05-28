package com.atv.backend.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {
    public List<ExchangeResponse> getExchangeRates() {
        // return a list of mock exchange rates
        return List.of(
                new ExchangeResponse("EUR", "USD", 1, 1.2),
                new ExchangeResponse("EUR", "RON", 1, 4.9),
                new ExchangeResponse("USD", "EUR", 1, 0.8),
                new ExchangeResponse("USD", "GBP", 1, 0.7),
                new ExchangeResponse("GBP", "RON", 1, 5.1),
                new ExchangeResponse("RON", "EUR", 1, 0.2),
                new ExchangeResponse("RON", "USD", 1, 0.3),
                new ExchangeResponse("RON", "GBP", 1, 0.1)
        );
    }

    public static class ExchangeResponse {
        private final String from;
        private final String to;
        private final double amount;
        private final double result;

        public ExchangeResponse(String from, String to, double amount, double result) {
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.result = result;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public double getAmount() {
            return amount;
        }

        public double getResult() {
            return result;
        }
    }
}
