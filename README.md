## This is a lab from the course "Spring Boot with databases"
It showcases querying complex data with:
+ JPQL
+ Criteria API

####The text of the task:

####Ukrainian:

Існує JPA сутність книжка з такими параметрами: назва, опис, к-сть сторінок, список жанрів, видавництво, тип палітурки, ціна, список авторів.
Через JPQL та Criteria API реалізувати запити: отримати всі книжки конкретного автора, всі книжки групи авторів, всі книжки де вказаний хоч один з вказаних авторів, всі книжки в діапазоні цін, всі книжки з конкретною ціною, аналогічні запити по видавництвам, жанрам, сторінкам, можливість шукати фрагмент тексту скрізь: в описі, назві, авторах, видавництві. Кожен запит є окремим методом сервісу.
Написати метод, що дозволяє передати набір цих параметрів об’єднаних через and або or та отримати відповідні книжки(аналогом є фільтр на сайті пошуку книжок). Всі параметри опційні.
Бажано додати тести хоча б на частину запитів.

####English:

There is a JPA entity book with the following parameters: title, description, number of pages, list of genres, publisher, type of cover, price, list of authors.
Through JPQL and Criteria API implement queries: get all books by a specific author, all books by a group of authors, all books where at least one of these authors is specified, all books in the price range, all books with a specific price, similar queries for publishers, genres, pages, a query created to search for a piece of text everywhere: in the description, title, authors, publisher. Each request is a separate method of service.
Write a method that allows you to pass a set of these parameters combined through "and" or "or" and get the corresponding books (similar to the filter on the book search site). All parameters are optional.
It is desirable to add tests for at least some of the queries.