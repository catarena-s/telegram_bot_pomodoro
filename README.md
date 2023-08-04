# Telegram bot "Pomodoro"

![Java](https://img.shields.io/badge/java-17-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-v3.1.2-%236DB33F.svg?style=flat&logo=spring&logoColor=white)
![postgresql](https://img.shields.io/badge/postgresql-v_42.6.0-blue.svg?style=flat&logo=postgresql&logoColor=white)
![HikariCP](https://img.shields.io/badge/Hikari--CP-v_3.4.5-blue.svg?style=flat&logo=HikariCP&logoColor=white)
![telegrambots](https://img.shields.io/badge/TelegramBots-v6.1.0-blue.svg?style=flat&logo=telegram&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18.24-red.svg?style=flat&logo=Lombok&logoColor=white)
![Intellj_Idea](https://img.shields.io/badge/intellj_Idea-v2022.12-blue.svg?style=flat&logo=intellij-idea&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-v7.4-green.svg?style=flat&logo=gradle&logoColor=white)

«Метод помидора» — техника управления временем, предложенная Франческо Чирилло в конце 1980-х.
Методика предполагает увеличение эффективности работы при меньших временных затратах за счёт
глубокой концентрации и коротких
перерывов. [Википедия](https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D1%82%D0%BE%D0%B4_%D0%BF%D0%BE%D0%BC%D0%B8%D0%B4%D0%BE%D1%80%D0%B0)

### Базовые принципы

1. Определитесь с задачами, которые планируете выполнять, расставьте приоритеты (см. матрица Эйзенхауэра)
2. Установите таймер на 20-25 минут.
3. Работайте, ни на что не отвлекаясь, до сигнала таймера
4. Сделайте короткий перерыв (5 минут).
5. После каждого 4-го «помидора» делайте длинный перерыв (15-30 минут).

## Доступные команды

| Команда        | Меню телеграм бота     | Описание                                |
|----------------|------------------------|-----------------------------------------|
| /start         |                        | Запустить бот                           |
| /help          | ❓ Помощь               | Подсказка по работе с ботом             |
| /run           | ▶️ таймер              | Запустить таймер Pomodoro               |
| /demo          | ▶️ демо                | Запустить Pomodoro в demo-режиме        |
| /check         | ⚙️ Настройки           | Запрашиваются текущие настройки таймера |
| /pomodoro-info | 💡 Что такое помодоро? | Краткая информация о "Помодоро"         |
| /commands      | 📄 Команды             | Посмотреть список доступных команд      |
| /stop          | ⏳ stop                 | Остановить запущенный таймер            |

[//]: # (| /settings      | ✏️ Поменять настройки | Установить свои настройки для таймера   |)
![1.jpg](res%2F1.jpg)
![2.jpg](res%2F2.jpg)
![3.jpg](res%2F3.jpg)
![4.jpg](res%2F4.jpg)
![5.jpg](res%2F5.jpg)
![6.jpg](res%2F6.jpg)
![7.jpg](res%2F7.jpg)


## Запуск

    gradle build
    
    java -jar  build/libs/Pomodoro_Bot-0.0.1-SNAPSHOT.jar

