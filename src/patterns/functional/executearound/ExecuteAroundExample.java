package patterns.functional.executearound;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;

// Приклад 1: Управління файлами
class FileManager {

    public static void withFile(String filename, Consumer<BufferedWriter> action) {
        System.out.println("Відкриваю файл: " + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            action.accept(writer);
        } catch (IOException e) {
            System.err.println("Помилка роботи з файлом: " + e.getMessage());
        } finally {
            System.out.println("Файл закрито: " + filename);
        }
    }

    public static <T> T withFileReader(String filename, Function<BufferedReader, T> action) {
        System.out.println("Читаю файл: " + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return action.apply(reader);
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
            return null;
        } finally {
            System.out.println("Файл закрито для читання: " + filename);
        }
    }
}

// Приклад 2: Вимірювання часу
class TimeMeasurement {

    public static void measureTime(String operationName, Runnable operation) {
        System.out.println("Початок операції: " + operationName);
        long startTime = System.currentTimeMillis();
        try {
            operation.run();
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("Операція '" + operationName + "' завершена за " +
                    (endTime - startTime) + " мс");
        }
    }

    public static <T> T measureTime(String operationName, Function<Void, T> operation) {
        System.out.println("Початок операції з результатом: " + operationName);
        long startTime = System.currentTimeMillis();
        try {
            return operation.apply(null);
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("Операція '" + operationName + "' завершена за " +
                    (endTime - startTime) + " мс");
        }
    }
}

// Приклад 3: Кастомний ресурс
class CustomResource {
    private String name;

    private CustomResource(String name) {
        this.name = name;
        System.out.println("Створено ресурс: " + name);
    }

    public static void withResource(String name, Consumer<CustomResource> action) {
        CustomResource resource = new CustomResource(name);
        try {
            action.accept(resource);
        } finally {
            resource.cleanup();
        }
    }

    public void doWork(String work) {
        System.out.println("Ресурс " + name + " виконує: " + work);
    }

    public CustomResource process(String operation) {
        System.out.println("Обробка операції: " + operation);
        return this;
    }

    private void cleanup() {
        System.out.println("Очищення ресурсу: " + name);
    }
}

public class ExecuteAroundExample {

    public static void main(String[] args) {
        System.out.println("=== Патерн Execute Around - Функціональна реалізація ===");

        // Приклад 1: Робота з файлами
        System.out.println("\n--- Управління файлами ---");
        FileManager.withFile("test.txt", writer -> {
            try {
                writer.write("Привіт, світ!");
                writer.newLine();
                writer.write("Це тестовий файл.");
            } catch (IOException e) {
                System.err.println("Помилка запису: " + e.getMessage());
            }
        });

        // Приклад 2: Вимірювання часу
        System.out.println("\n--- Вимірювання часу ---");
        TimeMeasurement.measureTime("Складні обчислення", () -> {
            try {
                Thread.sleep(100); // Імітація роботи
                System.out.println("Виконую складні обчислення...");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Приклад 3: Кастомний ресурс
        System.out.println("\n--- Кастомний ресурс ---");
        CustomResource.withResource("DatabaseConnection", resource -> {
            resource.doWork("SELECT * FROM users");
            resource.process("UPDATE users SET active = true")
                    .process("COMMIT");
        });
    }
}