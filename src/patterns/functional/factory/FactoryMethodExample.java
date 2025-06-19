package patterns.functional.factory;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

// Базовий інтерфейс продукту
interface Document {
    void open();
    void save();
    String getType();

    // Default метод з загальною поведінкою
    default void printInfo() {
        System.out.println("Документ типу: " + getType());
    }

    // Factory method як default метод
    default void processDocument() {
        System.out.println("Обробка документу...");
        open();
        printInfo();
        save();
    }
}

// Конкретні реалізації документів
class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Відкриваю Word документ");
    }

    @Override
    public void save() {
        System.out.println("Зберігаю Word документ");
    }

    @Override
    public String getType() {
        return "Microsoft Word";
    }
}

class PDFDocument implements Document {
    @Override
    public void open() {
        System.out.println("Відкриваю PDF документ");
    }

    @Override
    public void save() {
        System.out.println("Зберігаю PDF документ");
    }

    @Override
    public String getType() {
        return "Adobe PDF";
    }
}

// Функціональна фабрика
class DocumentFactory {
    private static final Map<String, Supplier<Document>> documentCreators = new HashMap<>();

    static {
        // Реєстрація фабричних методів через Supplier
        documentCreators.put("word", WordDocument::new);
        documentCreators.put("pdf", PDFDocument::new);
    }

    // Основний фабричний метод
    public static Document createDocument(String type) {
        Supplier<Document> creator = documentCreators.get(type.toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("Невідомий тип документу: " + type);
        }
        return creator.get();
    }

    // Додавання нових типів документів у runtime
    public static void registerDocumentType(String type, Supplier<Document> creator) {
        documentCreators.put(type.toLowerCase(), creator);
    }
}

public class FactoryMethodExample {

    public static void processDocument(Document document) {
        document.processDocument();
        System.out.println("---");
    }

    public static void main(String[] args) {
        System.out.println("=== Фабричний метод - Функціональна реалізація ===");

        // Створення документів через фабрику
        processDocument(DocumentFactory.createDocument("word"));
        processDocument(DocumentFactory.createDocument("pdf"));

        // Додавання нового типу документу в runtime
        DocumentFactory.registerDocumentType("excel", () -> new Document() {
            @Override
            public void open() {
                System.out.println("Відкриваю Excel документ");
            }

            @Override
            public void save() {
                System.out.println("Зберігаю Excel документ");
            }

            @Override
            public String getType() {
                return "Microsoft Excel";
            }
        });

        processDocument(DocumentFactory.createDocument("excel"));
    }
}