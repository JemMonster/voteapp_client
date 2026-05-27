# Vote App — Android Client

> Клиент мобильного приложения для голосований.

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
  <img src="https://img.shields.io/badge/DI-Hilt-E91E63?style=for-the-badge&logo=google&logoColor=white"/>
  <img src="https://img.shields.io/badge/Network-Ktor-2E7D32?style=for-the-badge&logo=ktor&logoColor=white"/>
  <img src="https://img.shields.io/badge/Auth-Firebase%20Auth-FFCA28?style=for-the-badge&logo=firebase&logoColor=black"/>
</p>

---

## Что реализовано сейчас (курсовая версия)
- Экран **Sign in**: `signin`
- Экран **Лента голосований**: `feed`
- Архитектура: Clean Architecture (UI → ViewModel → UseCase → Repository)
- DI: Hilt
- Состояние: `StateFlow`

> Примечание: текущая авторизация на `SignInScreen` — временная заглушка (пока нет полного Firebase Auth flow).

---

## Архитектура

### Clean Architecture (client)
```text
presentation/
  screens/        ← Jetpack Compose UI
  viewmodel/      ← ViewModel + StateFlow ui-state

domain/
  usecase/        ← UseCases
  model/          ← domain-модели
  port/           ← repository interfaces

data/
  api/            ← HTTP adapter (DTO + сетевые вызовы через Ktor)
  repository/     ← Repository implementations
  remote/         ← baseUrl/HTTP client factory

di/
  AppModule.kt    ← Hilt composition root
```

---

## Поток данных
1. UI вызывает действие в ViewModel.
2. ViewModel вызывает UseCase.
3. UseCase обращается к Repository (порт).
4. Repository обращается к HTTP (data/api) и маппит DTO → domain model.
5. ViewModel обновляет `StateFlow` для UI.

---

## Стек технологий
- Kotlin 1.9+
- Android
  - Jetpack Compose
  - Material 3
  - Navigation Compose
- DI: Dagger Hilt
- Coroutines + StateFlow
- Сеть: Ktor Client
  - kotlinx.serialization
  - ContentNegotiation

---

## Запуск
### Android
1) Откройте проект: `kotlin/client/androidApp`
2) Убедитесь, что файл `google-services.json` подключён (лежит в `androidApp/`)
3) Запустите приложение через Android Studio.

---

## Конфигурация окружения
- `VoteApiConfig.baseUrl` задаётся в `kotlin/client/androidApp/.../di/AppModule.kt`
  - сейчас: `http://192.168.0.100:8080`

---

## Roadmap (что ещё нужно до уровня reference)
См. `TODO.md` в корне проекта.
