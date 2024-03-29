# Начало работы

Github - склонировать проект себе на ПК для последующего запуска и тестирования.

**Для запуска тестов на вашем ПК должно быть установлено следующее ПО:**

* IntelliJ IDEA

* Git

* Docker Desktop

* браузер Google Chrome 

# Установка и запуск

1. Запустить Docker Desktop

2. Запускаем контейнеры из файла `docker-compose.yml` командой в терминале:

```
docker-compose up
```

и проверяем, что контейнеры запустились командой:

```
docker-compose ps
```
3. Во втором терминале запустить SUT командой

- для конфигурации с базой данный MySql:

```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
```

- для конфигурации с базой данных PostgreSQL:

```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
```

В браузере открыть SUT в окне с адресом

`http://localhost:8080`

4. Запустить автотесты командой

для конфигурации с MySql


```
./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app" "-Ddb.username=app" "-Ddb.password=pass"
```

для конфигурации с postgresql

```
./gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app" "-Ddb.username=app" "-Ddb.password=pass"
```

5. Остановить контейнеры командой CTRL + C и после удалить контейнеры командой

```
docker-compose down
```

## Документация

- [План автоматизации тестирования](https://github.com/ElinaHaf/DiplomProject/blob/master/docs/Plan.md)
- [Отчёт по итогам автоматизированного тестирования]()
- [Отчёт по итогам автоматизации]()
