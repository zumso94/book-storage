# BOOK-STORAGE
BOOK-STORAGE - это Web-приложение для хранения книг.
С помощью этой программы можно сохранять и выгружать pdf-файлы книг, вести поиск по жанру или по автору книги, 
можно сделать книги приватными. Также предусмотрена регистрация аккаунта и его подтверждение по почте. 
В разработке используются такие технологии как: Spring(Boot, Data JPA, Security, Web, AOP), PostgreSQl, Flyway.

На что следует обратить внимание:
- Подтверждение аккаунта через Email: https://github.com/zumso94/book-storage/blob/master/src/main/java/com/muslim/bookstorage/service/UserService.java
- Логирование методов через свою аннотацию и AOP: https://github.com/zumso94/book-storage/blob/master/src/main/java/com/muslim/bookstorage/aop/aspect/LoggingAspect.java
- Загрузка и выгрузка pdf-файлов: https://github.com/zumso94/book-storage/blob/master/src/main/java/com/muslim/bookstorage/controller/BooksController.java
- Exception Handler для обработки ошибок: https://github.com/zumso94/book-storage/blob/master/src/main/java/com/muslim/bookstorage/exception/ExceptionGlobalHandler.java
- Basic-authentication: https://github.com/zumso94/book-storage/tree/master/src/main/java/com/muslim/bookstorage/security
