# Тестовое задние.

Задание: https://github.com/dbeaver/info/wiki/Task-Weather-service

## Запуск приложения

Для запуска приложения на машине должны быть установлены docker и docker-compose. (docker v18.09.1 и выше,
docker-compose v1.21.0 и выше)

1. Выполнить клонирование репозитория.
2. Выполнить команду docker-compose up

## Edndpoitn

Приложение слушает порт 8082 (если на вашей машине данный порт занят, то указать свободный порт в *docker-compose.yml*).

Запрос: **GET: localhost:8082\weather**

Ответ: **{"date":"2022-01-17","temperature":-4}**

## Работа приложения

Приложение получает запрос. Проверяет БД на наличие погоды на дату запроса. Если в БД нет погоды на дату запроса, то
погода запрашивается с главной страницы *yandex.ru*, результат запроса сохраняется в БД и возвращается в ответ на
запрос.