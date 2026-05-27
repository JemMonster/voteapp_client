<h1 align="center">??? Vote App</h1>

<p align="center">
  <strong>Android-приложение для создания и проведения голосований.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
  <img src="https://img.shields.io/badge/Firebase-Auth-FFCA28?style=for-the-badge&logo=firebase&logoColor=black"/>
  <img src="https://img.shields.io/badge/DI-Hilt-E91E63?style=for-the-badge&logo=google&logoColor=white"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Architecture-Clean%20Architecture-orange?style=flat-square"/>
  <img src="https://img.shields.io/badge/Min%20SDK-24-blue?style=flat-square"/>
  <img src="https://img.shields.io/badge/Target%20SDK-35-blue?style=flat-square"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square"/>
</p>

---

## О проекте

**Vote App** — клиентская часть мобильного приложения для создания и проведения голосований. Приложение взаимодействует с REST API сервером, обеспечивает авторизацию через Firebase, создание различных типов голосований, участие в них и просмотр результатов.

---

## Функциональность

| Модуль | Описание |
|--------|----------|
| **Авторизация** | Firebase Auth (email + password), автоматическое определение сессии при запуске |
| **SignIn** | Экран входа в систему с валидацией ввода |
| **SignUp** | Экран регистрации нового пользователя |
| **Лента голосований** | Список активных голосований с возможностью быстрого доступа |
| **Детали голосования** | Просмотр информации о голосовании, участие в голосовании |
| **Создание голосования** | Формирование нового голосования с настройкой типа, опций и сроков |
| **Профиль** | Личный кабинет пользователя с информацией о профиле |
| **История голосований** | Список завершенных голосований с результатами |
| **Уведомления** | WorkManager-напоминания о голосованиях |

---

## Архитектура

```
presentation/
+-- screens/          < Jetpack Compose UI (экраны)
+-- viewmodel/        < ViewModel + StateFlow
L-- navigation/       < NavHost + навигация между экранами

domain/
+-- usecase/          < UseCases (бизнес-логика)
+-- model/            < доменные модели
L-- port/             < repository interfaces (ports)

data/
+-- api/              < Retrofit интерфейс + DTO-модели
+-- repository/       < Реализации репозиториев
L-- remote/           < baseUrl/HTTP client factory

di/                   < Hilt модули
```

**Паттерн:** Clean Architecture (MVVM)  
**DI:** Hilt (SingletonComponent)  
**Навигация:** Navigation Compose — auth > main, внутри main — экранный стек

---

## Стек технологий

| Категория | Библиотека / Инструмент |
|-----------|------------------------|
| Язык | Kotlin 2.0.20 |
| UI | Jetpack Compose + Material 3 |
| Навигация | Navigation Compose |
| DI | Dagger Hilt 2.51 |
| Сеть | Retrofit 2 + OkHttp |
| Сериализация | kotlinx.serialization |
| Auth | Firebase Authentication |
| Фоновые задачи | WorkManager |
| Архитектура | Clean Architecture, MVVM, StateFlow, Coroutines |
| Сборка | Gradle Kotlin DSL |
| Тестирование | JUnit 4, Mockk |

---

## Тесты

```bash
./gradlew :androidApp:testDebugUnitTest
```

| Тест | Покрытие |
|------|----------|
| `CreateVotingUseCaseTest` | Логика создания голосования |
| `GetVotingDetailUseCaseTest` | Получение деталей голосования |
| `SubmitVoteUseCaseTest` | Логика отправки голоса |
| `GetVotingHistoryUseCaseTest` | Получение истории голосований |

---

## Запуск

### Android
1. Откройте проект: `kotlin/client/androidApp`
2. Убедитесь, что файл `google-services.json` подключен (лежит в `androidApp/`)
3. Запустите приложение через Android Studio.

---

## Конфигурация окружения

- `VoteApiConfig.baseUrl` задаётся в `kotlin/client/androidApp/.../di/AppModule.kt`
  - текущее значение: `http://192.168.0.100:8080`

---

## Связанные репозитории

> Серверная часть (REST API) разрабатывается отдельно. Клиент ожидает API по схеме, описанной в соответствующих API-интерфейсах.

---

## Лицензия

Распространяется под лицензией **MIT**. Подробнее см. [LICENSE](LICENSE).
