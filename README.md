# JUnit Vanilla DevSuperior Example

A vanilla Java project demonstrating unit testing with **JUnit 5** (without Maven, Gradle, or Spring Boot), based on the DevSuperior *Financing* (`Financiamento`) exercise.

## Tech Stack & Requirements

- **Java**: 21 (`JavaSE-21`)
- **Testing Framework**: JUnit 5 (`org.junit.jupiter`)
- **IDE**: Eclipse / Spring Tool Suite (STS) — configured via `.project` and `.classpath` using the built-in `JUNIT_CONTAINER/5` library

## Domain & Business Rules

The `entities.Financing` class models a loan/financing agreement defined by three attributes:

- `totalAmount` (`Double`): total financed value
- `income` (`Double`): client's monthly income
- `months` (`Integer`): number of monthly installments

### Rules & Calculations

1. **Down payment (`entry()`)**: 20% of `totalAmount` (`totalAmount * 0.2`).
2. **Monthly installment (`quota()`)**: the remaining 80% divided evenly across `months` (`(totalAmount - entry()) / months`).
3. **Income limit validation**: the monthly installment **cannot exceed half of the client's income**:
   ```text
   (totalAmount * 0.8) / months <= income / 2.0
   ```
   - This rule is enforced in the constructor and in all setters (`setTotalAmount`, `setIncome`, `setMonths`).
   - If violated, an `IllegalArgumentException` is thrown with the message `"A parcela não pode ser maior que a metade da renda"`.

## Project Structure

```text
JunitVanillaDevSuperiorExample/
├── src/
│   ├── app/
│   │   └── Program.java                          # Console entry point demonstrating valid and invalid financing
│   └── entities/
│       ├── Financing.java                        # Financing entity with calculations and validation rules
│       └── tests/
│           ├── FinancingTests.java               # JUnit 5 unit tests for Financing
│           └── factory/
│               └── FinancingFactory.java         # Test factory creating a standard valid Financing instance
├── .classpath                                    # Eclipse classpath (JavaSE-21 + JUnit 5 container)
└── .project                                      # Eclipse / STS project descriptor
```

## Unit Tests

`entities.tests.FinancingTests` contains **10 unit tests** following the Arrange-Act-Assert (AAA) pattern, using `FinancingFactory.createValidFinancing()` (`totalAmount = 100,000.0`, `income = 2,000.0`, `months = 100`) and a floating-point tolerance of `0.000001`:

| Test Method | Scenario Verified |
| :--- | :--- |
| `constructorShouldCreateObjectWhenDataIsValid` | Instantiates `Financing` and sets all fields when values satisfy the income rule |
| `constructorShouldThrowExceptionWhenDataIsInvalid` | Throws `IllegalArgumentException` when installment exceeds 50% of income |
| `setTotalAmountShouldUpdateValueWhenDataIsValid` | Updates `totalAmount` when the resulting installment remains valid |
| `setTotalAmountShouldThrowExceptionWhenDataIsInvalid` | Throws `IllegalArgumentException` when the new `totalAmount` violates the limit |
| `setIncomeShouldUpdateValueWhenDataIsValid` | Updates `income` when the new income still supports the installment |
| `setIncomeShouldThrowExceptionWhenDataIsInvalid` | Throws `IllegalArgumentException` when the reduced `income` makes the installment invalid |
| `setMonthsShouldUpdateValueWhenDataIsValid` | Updates `months` when the new duration keeps the installment within the limit |
| `setMonthsShouldThrowExceptionWhenDataIsInvalid` | Throws `IllegalArgumentException` when fewer `months` make the installment exceed the limit |
| `entryShouldCalculateDownPaymentCorrectly` | Verifies `entry()` returns 20% of `totalAmount` (`20,000.0`) |
| `quotaShouldCalculateInstallmentCorrectly` | Verifies `quota()` returns the monthly installment (`800.0`) |

## How to Run

### In Eclipse / Spring Tool Suite (Recommended)

1. Go to **File → Import → General → Existing Projects into Workspace** and select this project directory.
2. Make sure **Java 21** is configured as the project JRE.
3. **Run the unit tests**: right-click `src/entities/tests/FinancingTests.java` → **Run As → JUnit Test**.
4. **Run the console demo**: right-click `src/app/Program.java` → **Run As → Java Application**.

### From the Command Line (Console Demo)

Compile and run `app.Program` directly with JDK 21:

```bash
javac -d bin src/entities/Financing.java src/app/Program.java
java -cp bin app.Program
```

*(Running `FinancingTests` from the CLI requires the JUnit 5 Platform Console Standalone JAR on the classpath, since JUnit 5 is provided by the IDE container.)*

## Sample Output (`app.Program`)

```text
Erro no financiamento 1: A parcela não pode ser maior que a metade da renda
Dados do financiamento 2:
Entrada: 20000.00
Prestação: 1000.00
```
*(Decimal separator depends on your JVM default locale.)*
