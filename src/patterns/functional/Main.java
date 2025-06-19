package patterns.functional;

import patterns.functional.strategy.StrategyPatternExample;
import patterns.functional.factory.FactoryMethodExample;
import patterns.functional.decorator.DecoratorPatternExample;
import patterns.functional.executearound.ExecuteAroundExample;

public class Main {

    private static void runStrategyExample() {
        System.out.println("1. ДЕМОНСТРАЦІЯ STRATEGY PATTERN");
        System.out.println("==================================================");
        StrategyPatternExample.main(new String[]{});
        System.out.println();
    }

    private static void runFactoryMethodExample() {
        System.out.println("2. ДЕМОНСТРАЦІЯ FACTORY METHOD PATTERN");
        System.out.println("==================================================");
        FactoryMethodExample.main(new String[]{});
        System.out.println();
    }

    private static void runDecoratorExample() {
        System.out.println("3. ДЕМОНСТРАЦІЯ DECORATOR PATTERN");
        System.out.println("==================================================");
        DecoratorPatternExample.main(new String[]{});
        System.out.println();
    }

    private static void runExecuteAroundExample() {
        System.out.println("4. ДЕМОНСТРАЦІЯ EXECUTE AROUND PATTERN");
        System.out.println("==================================================");
        ExecuteAroundExample.main(new String[]{});
        System.out.println();
    }
}