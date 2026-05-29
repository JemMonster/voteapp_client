<h1 align="center">Vote App</h1>

<p align="center">
  <strong>Android-приложение для голосований с чистой архитектурой.</strong>
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

**Vote App** — мобильное приложение для создания и участия в голосованиях. Реализовано с использованием REST API сервера, обеспечивает безопасную аутентификацию через Firebase, управление голосованиями, голосование и просмотр результатов.

**Язык интерфейса:** Русский

**Архитектура:** Clean Architecture + MVVM

---

## Реализованный функционал

| Функция | Описание |
|---------|----------|
| **Аутентификация** | Firebase Auth (email + password), верификация токена через Firebase |
| **Вход (SignIn)** | Экран авторизации с вводом email и пароля |
| **Регистрация (SignUp)** | Экран создания нового аккаунта пользователя |
| **Лента голосований** | Экран просмотра списка активных голосований с фильтрацией |
| **Поиск голосований** | Экран поиска с поисковой строкой, историей и фильтрацией |
| **Детали голосования** | Просмотр деталей, участие в голосовании, просмотр результатов |
| **История голосований** | Экран просмотра истории голосований пользователя |
| **Профиль** | Экран профиля с настройками и выходом из аккаунта |
| **Создание голосования** | Экран создания нового голосования |
| **Тёмная тема** | Переключатель светлой/тёмной темы с сохранением |
| **Уведомления** | WorkManager-уведомления о предстоящих голосованиях |

---

## Требования к поиску

### Экран поиска реализует:

1. **Поисковая строка:**
   - Подсказка (placeholder) в пустом поле: "Введите запрос для поиска..."
   - При нажатии на поле появляется клавиатура
   - Кнопка "Очистить" отображается только при наличии текста
   - Нажатие на "Очистить" удаляет текст и скрывает клавиатуру

2. **Сохранение состояния:**
   - Текст поискового запроса сохраняется при повороте устройства
   - Результаты поиска сохраняются в жизненном цикле

3. **Плейсхолдеры:**
   - Если нет результатов поиска — отображается сообщение с иконкой
   - При ошибке поиска — отображается сообщение с кнопкой "Обновить"
   - Повторное нажатие "Обновить" отправляет последний запрос

4. **История поиска:**
   - При нажатии на поисковую строку отображается история (если не пуста)
   - Хранится максимум 10 элементов
   - Новые элементы отображаются в верхней части списка
   - Нажатие на элемент истории добавляет его в поиск
   - Кнопка "Очистить историю" полностью очищает историю

5. **Индикация загрузки:**
   - ProgressBar отображается при выполнении поискового запроса

---

## Требования к темной теме

### Реализация переключателя тем:

1. **Доступность:**
   - Переключатель доступен на экране ленты (в верхней панели)
   - Переключатель доступен на экране профиля

2. **Функциональность:**
   - Переключение между светлой и тёмной темой
   - Сохранение выбора темы при закрытии приложения
   - Мгновенное применение темы ко всему приложению

3. **Визуализация:**
   - Иконка показывает текущее состояние (солнце/луна)
   - На экране профиля отображается название текущей темы

---

## Требования к языку

- **Весь интерфейс на русском языке**
- Все кнопки, заголовки, подсказки, сообщения об ошибках — на русском
- Язык приложения не зависит от системного языка устройства

---

## Структура проекта

```
presentation/
+-- search/             # Экран поиска (SearchScreen, SearchViewModel)
+-- signin/             # Экраны входа и регистрации
+-- feed/               # Экран ленты голосований
+-- votingdetail/       # Экран деталей голосования
+-- history/            # Экран истории голосований
+-- profile/            # Экран профиля
+-- createvoting/       # Экран создания голосования
+-- components/         # Переиспользуемые UI компоненты
+-- theme/              # Управление темой (ThemeViewModel)

domain/
+-- usecase/            # UseCases (бизнес-логика)
+-- model/              # Доменные модели
L-- port/               # repository interfaces (ports)

data/
+-- api/                # Retrofit API + DTO-модели
+-- repository/         # Реализация репозиториев
L-- mapper/             # DTO ↔ Domain мапперы

di/                     # Hilt модули
ui/theme/               # Цвета и темы
```

**Архитектура:** Clean Architecture (MVVM)  
**DI:** Hilt (SingletonComponent)  
**Навигация:** Navigation Compose с auth > main, main с поиском

---

## Технологический стек

| Технология | Описание |
|------------|----------|
| Язык | Kotlin 2.0.20 |
| UI | Jetpack Compose + Material 3 |
| Навигация | Navigation Compose |
| DI | Dagger Hilt 2.51 |
| Сеть | Retrofit 2 + OkHttp |
| Сериализация | kotlinx.serialization |
| Auth | Firebase Authentication |
| Уведомления | WorkManager |
| Архитектура | Clean Architecture, MVVM, StateFlow, Coroutines |
| Сборка | Gradle Kotlin DSL |
| Тесты | JUnit 4, Mockk |

---

## Запуск тестов

```bash
./gradlew :androidApp:testDebugUnitTest
```

| Тест | Описание |
|------|----------|
| `CreateVotingUseCaseTest` | Логика создания голосования |
| `GetVotingDetailUseCaseTest` | Получение деталей голосования |
| `SubmitVoteUseCaseTest` | Логика отправки голоса |
| `GetVotingHistoryUseCaseTest` | Получение истории голосований |
| `VotingDetailViewModelTest` | ViewModel деталей голосования |
| `ProfileViewModelTest` | ViewModel профиля |
| `VotingHistoryViewModelTest` | ViewModel истории |
| `CreateVotingViewModelTest` | ViewModel создания голосования |

---

## Сборка и запуск

### Android
1. Открыть проект в Android Studio
2. Убедиться, что файл `google-services.json` присутствует (должен быть в `androidApp/`)
3. Запустить приложение через Android Studio

### Настройка сервера
- `VoteApiConfig.baseUrl` изменить в `kotlin/client/androidApp/.../di/AppModule.kt`
  - Адрес по умолчанию: `http://192.168.0.100:8080`

---

## Примечания к серверу

> Серверная часть (REST API) разворачивается отдельно. См. документацию в `server/` и актуальную API-документацию.

---

## Лицензия

Распространяется под лицензией **MIT**. См. файл [LICENSE](LICENSE).
