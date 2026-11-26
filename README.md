# Лабораторна робота №1 — варіант 2

Основи Java та модифікатори доступу.

![build](https://github.com/Java-Fall2026/lab1-babiukanhelina-ui/actions/workflows/build.yml/badge.svg)

- Умова: https://github.com/Java-Univer-2025/Java-Univer-2025.github.io/blob/main/lab-01/README.md
- Домени та поля: https://github.com/Java-Univer-2025/Java-Univer-2025.github.io/blob/main/lab-01/tasks_2025_domains.md

## Предметна область

Варіант 2 — **бронювання столиків у ресторані** (`ua.restaurant`). Реалізовано чотири сутності з профілю: `Customer`, `RestaurantTable`, `Reservation`, `RestaurantOrder`.

## Структура

```text
src/main/java/
├── ua/common/BaseEntity.java          готовий клас, не змінюйте
└── ua/restaurant/
    ├── Main.java                      демонстраційний клас
    ├── model/
    │   ├── Customer.java
    │   ├── RestaurantTable.java
    │   ├── Reservation.java
    │   └── RestaurantOrder.java
    └── util/
        ├── ValidationHelper.java      package-private
        ├── FormatHelper.java          package-private
        └── RestaurantUtils.java       public
```

`BaseEntity.java` залишено без змін. Усі чотири сутності успадковують `BaseEntity` та викликають `super()`.

## Поля та правила доменного профілю

- `Customer`: `phone` — непорожній 🔑; `name` — непорожній; `email` — непорожній і містить `@`.
- `RestaurantTable`: `tableNumber` — від 1 до 200 🔑; `capacity` — від 1 до 20; `zone` — `MAIN_HALL`, `TERRACE`, `VIP`, `BAR`.
- `Reservation`: `customer` і `table` — не `null`; `reservationDate`, `startTime`, `endTime` — не `null`; `endTime` пізніше `startTime`; `guestCount` — від 1 до місткості столика; `status` ✎ — `PENDING`, `CONFIRMED`, `SEATED`, `COMPLETED`, `CANCELLED`.
- `RestaurantOrder`: `orderId` — непорожній 🔑; `reservation` — не `null`; `totalAmount` — не менше 0; `discountAmount` ✎ — від 0 до `totalAmount`.

Для `Reservation`, яка позначена в профілі як сутність без готового ключа 🔑❓, обрано складену identity: **столик + дата + час початку + час завершення**. Два бронювання вважаються рівними, якщо вони описують той самий часовий слот того самого столика; клієнт, статус і кількість гостей не змінюють identity слота.

## Модифікатори доступу та створення об'єктів

- Поля сутностей — `private`; сеттери є лише для полів із позначкою ✎: `Reservation.status` і `RestaurantOrder.discountAmount`.
- `Customer` і `Reservation` мають `private` конструктори та публічні фабричні методи `of(...)`.
- `RestaurantTable` і `RestaurantOrder` створюються публічними конструкторами.
- `ValidationHelper` і `FormatHelper` — package-private; `RestaurantUtils` — `public`.
- Переліки нормалізуються до `UPPERCASE`; помилки валідації кидають `IllegalArgumentException` з очікуваним і отриманим значеннями.
- Кожна сутність реалізує `equals()`, `hashCode()` і `toString()`; `createdAt` не входить до identity.

## Обчислювані методи

- `RestaurantUtils.durationMinutes(Reservation)` — тривалість бронювання у хвилинах.
- `RestaurantUtils.finalAmount(RestaurantOrder)` — сума замовлення мінус знижка.

## Як зібрати і запустити

Потрібен **JDK 21**. Перевірте в PowerShell:

```powershell
java -version
javac -version
```

У Windows відкрийте PowerShell у кореневій папці проєкту, де розташовано `gradlew.bat`, і виконайте:

```powershell
.\gradlew.bat clean build
.\gradlew.bat run
```

У Linux/macOS:

```bash
./gradlew clean build
./gradlew run
```

Перед запуском у `build.gradle` налаштовано:

```groovy
application {
    mainClass = 'ua.restaurant.Main'
}
```

## Частина про AI

Відомості про використання AI, недоліки первинної версії, внесені виправлення та критичну оцінку наведено у файлі `AI.md`.

## GitHub Actions

Після кожного push запускається workflow `.github/workflows/build.yml`, який встановлює Temurin JDK 21 і виконує `./gradlew build --no-daemon`. Зелений бейдж вище означає, що CI-збірка пройшла.

## Чекліст здачі

- [x] Усі сутності успадковують `BaseEntity` та викликають `super()`.
- [x] Поля приватні; сеттери лише для позначених у профілі `✎`.
- [x] Два класи мають приватні конструктори й фабричні `of(...)`.
- [x] Валідація в конструкторах і сеттерах повідомляє очікуване та отримане значення.
- [x] `ValidationHelper` і `FormatHelper` — package-private; `RestaurantUtils` — public.
- [x] Реалізовано два обчислювані методи з профілю.
- [x] Кожна сутність має `equals()`, `hashCode()`, `toString()`.
- [x] `Main` демонструє успішні та невдалі сценарії.
- [x] `AI.md` заповнено.
- [ ] Зелений бейдж GitHub Actions після push.

## Здача

Код у цьому репозиторії + захист 5 хвилин. Без захисту робота не зарахована.
