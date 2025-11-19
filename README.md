```markdown
# Kafka Log Appender Demo

Проект демонстрирует интеграцию Spring Boot приложения с кастомной библиотекой KafkaAppender
для отправки логов в различные топики в зависимости от их уровня.

## Архитектура

- **Spring Boot Application** - генерирует логи разных уровней
- **Apache Kafka** - брокер сообщений
- **Kafka UI** - веб-интерфейс для мониторинга топиков и сообщений
- **Custom Logback Appender** - отправляет логи в соответствующие Kafka топики исходя из log4j2.xml конфигурации

## Запуск проекта

### 1. Запуск инфраструктуры
```bash
docker-compose up -d
```

### 2. Проверка контейнеров
```bash
docker ps
```

Ожидаемый вывод:
```
CONTAINER ID   IMAGE                              PORTS                    NAMES
xxxxxxxxxxxx   confluentinc/cp-kafka:7.4.0       0.0.0.0:9092->9092/tcp   <container_name>
xxxxxxxxxxxx   provectuslabs/kafka-ui:latest     0.0.0.0:8082->8080/tcp   <container_name>
xxxxxxxxxxxx   confluentinc/cp-zookeeper:7.4.0   0.0.0.0:2181->2181/tcp   <container_name>
```

### 3. Создание топиков Kafka
```bash
# Замените <container_name> на актуальное имя контейнера Kafka
docker exec <container_name> kafka-topics --bootstrap-server localhost:9092 --create --topic prod-error-logs --partitions 1 --replication-factor 1
docker exec <container_name> kafka-topics --bootstrap-server localhost:9092 --create --topic prod-warn-logs --partitions 1 --replication-factor 1
docker exec <container_name> kafka-topics --bootstrap-server localhost:9092 --create --topic prod-info-logs --partitions 1 --replication-factor 1
docker exec <container_name> kafka-topics --bootstrap-server localhost:9092 --create --topic prod-debug-logs --partitions 1 --replication-factor 1
```

### 4. Проверка созданных топиков
```bash
docker exec <container_name> kafka-topics --bootstrap-server localhost:9092 --list
```

Ожидаемый вывод:
```
prod-debug-logs
prod-error-logs
prod-info-logs
prod-warn-logs
```

### 5. Запуск Spring приложения
```bash
./mvnw spring-boot:run
```

Или через IDE:
- Запустите главный класс `KafkaAppenderDemoApplication`

### 6. Мониторинг логов

#### Через Kafka UI
Откройте в браузере: http://localhost:8082

#### Через консоль Consumer
```bash
# Для просмотра всех логов
docker exec -it <container_name> kafka-console-consumer --bootstrap-server localhost:9092 --topic logs --from-beginning

# Для конкретного топика (замените <topic_name>)
docker exec -it <container_name> kafka-console-consumer --bootstrap-server localhost:9092 --topic <topic_name> --from-beginning
```

## Структура топиков

| Уровень лога | Kafka топик     | Назначение                            |
|--------------|-----------------|---------------------------------------|
| ERROR        | prod-error-logs | Критические ошибки                    |
| WARN         | prod-warn-logs  | Предупреждения                        |
| INFO         | prod-info-logs  | Информационные сообщения              |
| DEBUG        | logs            | Отладочная информация  дефолтном логе |

