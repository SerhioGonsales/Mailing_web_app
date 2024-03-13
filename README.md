##Mailer_web_app ![](logo.png)

Приложение для организации "нежной" email-рассылки, предназначенное 
для самозанятых и небольших ИП. Когда нет необходимости в крупных и 
серьезных рассылках и
мощных рекламных акциях, но есть необходимость в автоматизации
рутины по уведомлению ваших клиентов о новых услугах или
изменениях в тарифах.

### Особенности:
- **Безопасность**: Отправка одного письма каждые 30 минут в рабочие часы с 9:00 до 18:00,
дает повышенные шансы избежать блокировки со стороны почтового сервера.
- **Уникальность**: Каждое следующее письмо может быть не похоже на 
предыдущее при условии если загрузки нескольких вариантов текстов и 
тем для писем. В этом случае при отправке приложение создаст набор 
из уникальных шаблонов.

После запуска приложения вы получите web-страницу по адресу 
```http://localhost:8080``` для реализации простых, 
но полезных функций, таких как:

_**Вкладка "Адреса"**_
- загрузка файла *.xlsx со списком email адресов
- инструменты для манипуляции с базой данных для рассылки

_**Вкладка "Письма"**_
- добавление тем для писем
- загрузка файла *.html с текстом для тела письма
- загрузка любого файл для прикрепления к письмам

_**Вкладка "Рассылка"**_
- отправка тестового письма себе на почту
- начало рассылки

###Инструкция по запуску проекта на локальном компьютере 
1. Установить [Docker](https://www.docker.com/products/docker-desktop/) 

2. Скопировать [репозиторий](https://github.com/SerhioGonsales/Mailing_web_app.git) 
или просто загрузить [архив](https://github.com/SerhioGonsales/Mailing_app/archive/refs/heads/master.zip) с приложением  
3. В файл ```.env``` файл вставить свои логин и пароль для приложений для Яндекс почты.
Пока приложение работает только для этого сервиса. Ниже инструкция, что настроить в почте и
где найти пароль для приложения.

> _Шестерёнка -> все настройки -> безопасность -> пароли приложений 
> -> почта -> создать новый пароль для приложения_

> _Шестерёнка -> все настройки -> почтовые программы -> 
> поставить галочку в "С сервера imap.yandex.ru ..."_

4. Запустить приложение из командной строки
```sh
docker-compose up
```