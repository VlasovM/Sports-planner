# Приложение Sport-planner
[English version bellow](#Sports-planner-application)
<div id="badges">
  <a href="https://openjdk.org/projects/jdk/17/">
    <img src="https://img.shields.io/badge/Java%3A%20-17%20-%20darkcyan?style=flat&logo=openjdk" alt="JDK Badge"/>
  </a>
  <a href="https://spring.io/blog/2024/05/23/spring-boot-3-3-0-available-now">
    <img src="https://img.shields.io/badge/Spring%20boot%3A%20-3.3.0%20-%20darkcyan?style=flat&logo=Spring" alt="Spring Badge"/>
  </a>
  <a href="https://maven.apache.org/">
    <img src="https://img.shields.io/badge/Apache%20Maven%20-%20darkcyan?style=flat&logo=Apache-Maven" alt="Maven Badge"/>
  </a>
</div>

Это приложение состоит из трёх небольших микросервисов, по-разному взаимодействующих друг с другом. 

Благодаря одной части приложения пользователь может записывать результаты своих тренировок, турниров, писать и читать
статьи, а также отслеживать результаты проверки здоровья.

Другая же часть приложения имитирует функционал для врача, который должен вносить результат о проверке здоровья
спортсмена, прошедшего у него осмотр.

## Содержание:
- [Начало работы](#Начало-работы)
- [Технологии](#Технологии)
- [Архитектура](#Архитектура)
- [База данных](#База-данных)
- [Тестирование](#Тестирование)
- [Планируемые доработки](#Планируемые-доработки)
- [Контакты](#Контакты)

## Начало работы:
Для того, чтобы начать работать с приложением необходимо склонировать проект к тебе на машину командой:

    git clone https://github.com/VlasovM/Sports-planner.git

После установки проекта необходимо установить Docker desktop, сделать это можно по инструкции:

https://docs.docker.com/desktop/install/windows-install/

Далее вам необходимо зайти в папку проекта, открыть консоль и ввести команду:

    mvn clean package

После успешного выполнения данной команды необходимо проинициализировать и запустить контейнер с образами наших приложений.
Сделать это моожно командой: 

    docker compose up

В случае успеха всех предыдущих шагов, в интерфейсе Docker desktop отобразится контейнер **sports-planner** и 7 образов приложений.

Это значит, наши приложения готовы к работе.

Приложение **planner** будет доступно по ссылке: http://localhost:8080/

Доступные пользователи:
- VolkovLI@planner.ru | Volkov
- MoskvinVV@planner.ru | Moskvin
- EliseevaEA@planner.ru | Eliseeva

Приложение **clinic** будет доступно по ссылке: http://localhost:8091/

Доступные пользователи:
- VolkovLI@clinic.ru  | Volkov
- MoskvinVV@clinic.ru  | Moskvin
- EliseevaEA@clinic.ru  | Eliseeva

Инструкцию и данные для подключения к базам данных смотреть в разделе [База данных.](#База-данных)

## Технологии:
- Сборщик проектов: Maven
- Основной фреймвок: Spring Boot 3.3.0
- СУБД:
  - H2 v.2.2.220 (только для тестирования)
  - PostgreSQL v.42.6.0
- Тестирование:
  - JUnit 5
  - Mockito-core
  - Spring boot test
  - Spring security test
- Разработка фронтенда:
  - Spring thymeleaf
  - Jquery
- Другие зависимости:
  - Spring data jpa
  - Spring security
  - Spring kafka
  - Lombok
  - Map struct
  - Hibernate-validator
  - Docker

## Архитектура:

Взаимодействие между Planner-application и Journaling-application происходит через Kafka в односторонней связи
(только Planner отправляет данные в Journaling и никак иначе).

Между Clinic-application и Planner-application взаимодействие по REST.
<h2>![image](./readme_assets/Architecture sports-planner.PNG)</h2>

## База данных:
Основная СУБД в проекте: **PostgreSQL**  
СУБД для тестов: **H2**

ER-диаграмма базы данных planner (модуль Planner + Journaling):
<h2>![image](./readme_assets/planner%20DB.PNG)</h2>

ER-диаграмма базы данных clinic (модуль Clinic):
<h2>![image](./readme_assets/clinic%20DB.PNG)</h2>

## Тестирование:
Тесты написаны только для модуля Planner.
В основном тесты покрывают слои: repository (кастомные запросы), service, controller, mapper, handler.

Для слоя repository написаны интеграционные тесты с использованием H2.  
Для слоев service, mapper, handler используются mock тесты.  
Для слоя controller испольуется технология mockMvc.

## Планируемые доработки:
- Добавить аутентификацию через JWT;
- Написать тесты для модуля Clinic;
- Поменять базу данных для модуля Journaling с SQL на NoSQL;
- Добавить отчёт о покрытии кода тестами;
- Добавить Spring Cloud: Circuit Breaker, OpenFeign;

## Контакты:
По любым вопросам и предложениям можно писать мне в tg: https://t.me/JaVlasov


# Sports-planner application
<div id="badges">
  <a href="https://openjdk.org/projects/jdk/17/">
    <img src="https://img.shields.io/badge/Java%3A%20-17%20-%20darkcyan?style=flat&logo=openjdk" alt="JDK Badge"/>
  </a>
  <a href="https://spring.io/blog/2024/05/23/spring-boot-3-3-0-available-now">
    <img src="https://img.shields.io/badge/Spring%20boot%3A%20-3.3.0%20-%20darkcyan?style=flat&logo=Spring" alt="Spring Badge"/>
  </a>
  <a href="https://maven.apache.org/">
    <img src="https://img.shields.io/badge/Apache%20Maven%20-%20darkcyan?style=flat&logo=Apache-Maven" alt="Maven Badge"/>
  </a>
</div>

This application consist of three microservices, different connect each other.

First part can help user can write result of his workout, tournaments. Read and write article and read result of 
health check.

Another part make imitation clinic. User in the role doctor can make result of health check his patient.

## Content
- [Getting started](#Getting-started)
- [Technologies](#Technologies)
- [Architecture](#Architecture)
- [Data base](#Data-base)
- [Test](#Test)
- [To Do](#To-Do)
- [Contacts](#Contacts)

## Getting-started
For start you need to clone this application in your PC next command:

    git clone https://github.com/VlasovM/Sports-planner.git

After installing the project you need to install Docker desktop. Instruction, how to do it:

https://docs.docker.com/desktop/install/windows-install/

Then you need to move to project folder, open console and enter the command:

    mvn clean package

After succeed run this command you need create and run docker container with next command:

    docker compose up

If all last step is succeed, in interface Docker desktop you will see the container **sports-planner** and seven image with all part application.

Now, our application is running and ready to work.

Application **planner** will be available to link: http://localhost:8080/

Available users:
- VolkovLI@planner.ru | Volkov
- MoskvinVV@planner.ru | Moskvin
- EliseevaEA@planner.ru | Eliseeva

Application **clinic** will be available to link: http://localhost:8091/

Available users:
- VolkovLI@clinic.ru  | Volkov
- MoskvinVV@clinic.ru  | Moskvin
- EliseevaEA@clinic.ru  | Eliseeva

Instruction and data for connect to database you can see in chapter [Database.](#Data-base)

## Technologies:
- Project builder: Maven
- Main framework: Spring Boot 3.3.0
- DBMS:
  - H2 v.2.2.220 (only for testing)
  - PostgreSQL v.42.6.0
- Test:
  - JUnit 5
  - Mockito-core
  - Spring boot test
  - Spring security test
- Develop frontend:
  - Spring thymeleaf
  - Jquery
- Other dependency:
  - Spring data jpa
  - Spring security
  - Spring kafka
  - Lombok
  - Map struct
  - Hibernate-validator
  - Docker

## Architecture:

Interaction between Planner-application and Journaling-application make from Kafka with unidirectional relation
(only Planner send data to Journaling and no other way).

Between Clinic-application and Planner-application interaction by REST.
<h2>![image](./readme_assets/Architecture sports-planner.PNG)</h2>

## База данных:
Main DBMS in project: **PostgreSQL**  
DBMS for tests: **H2**

ERD database planner (module Planner + Journaling):
<h2>![image](./readme_assets/planner%20DB.PNG)</h2>

ERD database clinic (module Clinic):
<h2>![image](./readme_assets/clinic%20DB.PNG)</h2>

## Test:
Tests completed only for module Planner.
Tests coverage: repository (custom query), service, controller, mapper, handler.

For repository make integration tests with H2.
For service, mapper, handler use mock tests.
For controller uses mockMvc.

## To Do:
- Added authentication from JWT;
- Create tests for module Clinic;
- Replace database for logs (module Journaling) from SQL to NoSQL;
- Add repost for coverage code;
- Add Spring Cloud: Circuit Breaker, OpenFeign;

## Contacts:
For any questions and suggestions you can write me to tg: https://t.me/JaVlasov
