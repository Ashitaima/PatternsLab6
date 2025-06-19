package patterns.functional.strategy;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class StrategyPatternExample {

    // Функціональний підхід - використання Predicate як стратегії
    public static double calculatePrice(List<Double> prices, Function<Double, Double> strategy) {
        return prices.stream().mapToDouble(strategy::apply).sum();
    }

    // Додатковий приклад з фільтрацією
    public static double calculateFilteredPrice(List<Double> prices,
                                                Predicate<Double> filter,
                                                Function<Double, Double> strategy) {
        return prices.stream()
                .filter(filter)
                .mapToDouble(strategy::apply)
                .sum();
    }

    // Предвизначені стратегії як статичні методи
    public static double applyDiscount(double price) {
        return price * 0.9; // 10% знижка
    }

    public static double applyTax(double price) {
        return price * 1.2; // 20% податок
    }

    public static double applyPremium(double price) {
        return price * 1.5; // 50% надбавка
    }

    public static void main(String[] args) {
        List<Double> prices = List.of(100.0, 250.0, 75.0, 300.0, 50.0);

        System.out.println("=== Патерн Стратегія - Функціональна реалізація ===");

        // Використання лямбда-виразів
        System.out.println("Загальна сума: " +
                calculatePrice(prices, price -> price));

        System.out.println("Сума зі знижкою 10%: " +
                calculatePrice(prices, price -> price * 0.9));

        System.out.println("Сума з податком 20%: " +
                calculatePrice(prices, price -> price * 1.2));

        // Використання method references
        System.out.println("Сума зі знижкою (method ref): " +
                calculatePrice(prices, StrategyPatternExample::applyDiscount));

        // Композиція стратегій
        Function<Double, Double> discountAndTax =
                ((Function<Double, Double>) StrategyPatternExample::applyDiscount)
                        .andThen(StrategyPatternExample::applyTax);

        System.out.println("Сума зі знижкою + податок: " +
                calculatePrice(prices, discountAndTax));

        // Фільтрація з стратегією
        System.out.println("Сума дорогих товарів (>100) з премією: " +
                calculateFilteredPrice(prices,
                        price -> price > 100,
                        StrategyPatternExample::applyPremium));
    }
}