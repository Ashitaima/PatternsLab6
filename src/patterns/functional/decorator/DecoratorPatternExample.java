package patterns.functional.decorator;

import java.util.function.Function;
import java.util.stream.Stream;

// Базовий клас для тексту
class TextProcessor {
    private Function<String, String> processor;

    public TextProcessor(Function<String, String>... decorators) {
        // Композиція декораторів через reduce та andThen
        this.processor = Stream.of(decorators)
                .reduce(Function.identity(), Function::andThen);
    }

    public String process(String text) {
        return processor.apply(text);
    }
}

// Статичні методи-декоратори
class TextDecorators {

    // Декоратор для переведення в верхній регістр
    public static String toUpperCase(String text) {
        System.out.println("Застосовую декоратор: UPPER CASE");
        return text.toUpperCase();
    }

    // Декоратор для додавання зірочок
    public static String addStars(String text) {
        System.out.println("Застосовую декоратор: STARS");
        return "*** " + text + " ***";
    }

    // Декоратор для додавання рамки
    public static String addBorder(String text) {
        System.out.println("Застосовую декоратор: BORDER");
        String border = "=" + "=".repeat(text.length() + 2) + "=";
        return border + "\n| " + text + " |\n" + border;
    }

    // Декоратор для додавання префіксу
    public static Function<String, String> addPrefix(String prefix) {
        return text -> {
            System.out.println("Застосовую декоратор: PREFIX (" + prefix + ")");
            return prefix + text;
        };
    }
}

public class DecoratorPatternExample {

    public static void main(String[] args) {
        System.out.println("=== Патерн Декоратор - Функціональна реалізація ===");

        String originalText = "Hello World";
        System.out.println("Оригінальний текст: " + originalText);
        System.out.println();

        // Простий декоратор
        System.out.println("--- Один декоратор ---");
        TextProcessor processor1 = new TextProcessor(TextDecorators::toUpperCase);
        System.out.println("Результат: " + processor1.process(originalText));
        System.out.println();

        // Кілька декораторів
        System.out.println("--- Кілька декораторів ---");
        TextProcessor processor2 = new TextProcessor(
                TextDecorators::toUpperCase,
                TextDecorators::addStars
        );
        System.out.println("Результат: " + processor2.process(originalText));
        System.out.println();

        // Складна композиція декораторів
        System.out.println("--- Складна композиція ---");
        TextProcessor processor3 = new TextProcessor(
                TextDecorators::toUpperCase,
                TextDecorators::addStars,
                TextDecorators::addBorder
        );
        System.out.println("Результат:\n" + processor3.process(originalText));
        System.out.println();

        // Декоратори з параметрами
        System.out.println("--- Декоратори з параметрами ---");
        TextProcessor processor4 = new TextProcessor(
                TextDecorators.addPrefix("[ВАЖЛИВО] "),
                TextDecorators::toUpperCase
        );
        System.out.println("Результат: " + processor4.process(originalText));
    }
}