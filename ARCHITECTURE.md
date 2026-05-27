# Clean Architecture — Vote App Client

## Принципы Clean Architecture

Этот проект следует принципам Clean Architecture Роберта Мартина:

### Dependency Rule

**Зависимости направлены ВНУТРЬ.** Внешние слои зависят от внутренних, но не наоборот.

```
┌─────────────────────────────────────────────────┐
│              Presentation Layer                  │
│         (ViewModels, Screens, UI)               │
└───────────────────┬─────────────────────────────┘
                    │ depends on
                    ↓
┌─────────────────────────────────────────────────┐
│                Domain Layer                      │
│   (UseCases, Repository Interfaces, Models)     │
└───────────────────┬─────────────────────────────┘
                    │ depends on
                    ↓
┌─────────────────────────────────────────────────┐
│                 Data Layer                       │
│    (Repositories, API, DTOs, Mappers)           │
└─────────────────────────────────────────────────┘
```

## Слой Presentation

**Отвечает за:** UI, навигацию, состояние экрана

**Зависит от:** Domain Layer (UseCases)

**Не зависит от:** Data Layer напрямую

```
presentation/
├── feed/
│   ├── FeedScreen.kt          # UI экран
│   └── FeedViewModel.kt       # ViewModel
├── signin/
│   ├── SignInScreen.kt
│   ├── SignUpScreen.kt
│   └── SignInViewModel.kt
└── ...
```

**ViewModel** использует только UseCases:

```kotlin
class FeedViewModel @Inject constructor(
    private val getVotingsUseCase: GetVotingsUseCase
) : ViewModel() {
    fun load() {
        viewModelScope.launch {
            val result = getVotingsUseCase() // Только UseCase
            // Ничего о Data Layer не знает
        }
    }
}
```

## Слой Domain

**Отвечает за:** Бизнес-логику, правила

**Зависит от:** НИЧЕГО (чистый Kotlin)

**Не зависит от:** Android, Retrofit, Firebase, и т.д.

```
domain/
├── model/              # Доменные модели (чистые POJO)
│   ├── Voting.kt
│   ├── VotingResult.kt
│   └── VotingType.kt
├── port/               # Repository interfaces (Ports)
│   └── VotingRepository.kt
└── usecase/            # Бизнес-логика
    ├── GetVotingsUseCase.kt
    ├── CreateVotingUseCase.kt
    └── ...
```

**UseCase** инкапсулирует одну операцию:

```kotlin
class GetVotingsUseCase(
    private val repository: VotingRepository // зависит от interface
) {
    suspend operator fun invoke(): List<Voting> {
        // Бизнес-логика получения голосований
        return repository.getVotings()
    }
}
```

**Repository Interface** (Port) определяется в Domain:

```kotlin
interface VotingRepository {
    suspend fun getVotings(): List<Voting>
    suspend fun createVoting(...): String
    // ...
}
```

## Слой Data

**Отвечает за:** Реализацию репозиториев, сетевые вызовы, маппинг

**Зависит от:** Domain Layer (реализует repository interface)

**Не экспонирует:** DTO наружу

```
data/
├── api/                    # Retrofit API интерфейс (DTOs)
│   ├── ApiService.kt
│   ├── VotingDto.kt        # DTO - только для data слоя!
│   └── ...
├── mapper/                 # Мапперы DTO ↔ Domain
│   └── VotingMapper.kt
└── repository/             # Реализации репозиториев
    └── VotingRepositoryImpl.kt
```

### Ключевое правило: DTO не выходят из Data слоя

**Правильно:**

```
API (DTO) → RepositoryImpl (маппинг) → Domain Model → UseCase → ViewModel
```

**Неправильно (нарушение Clean Architecture):**

```
API (Domain Model) → Repository → ... ❌
```

**В нашем проекте:**

```kotlin
// 1. API возвращает DTO
interface ApiService {
    suspend fun getVotings(): VotingsResponseDto // DTO
}

// 2. RepositoryImpl делает маппинг
class VotingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : VotingRepository {
    override suspend fun getVotings(): List<Voting> {
        val response = apiService.getVotings() // Получаем DTO
        return response.items.map { it.toDomain() } // Маппим в Domain
    }
}

// 3. UseCase получает Domain Model
class GetVotingsUseCase(
    private val repository: VotingRepository
) {
    suspend operator fun invoke(): List<Voting> {
        return repository.getVotings() // Получаем Domain Model
    }
}
```

## Маппинг DTO ↔ Domain

Все мапперы находятся в `data/mapper/`:

```kotlin
// DTO → Domain
fun VotingDto.toDomain(): Voting {
    return Voting(
        id = this.id,
        title = this.title,
        type = this.type.toDomain(), // enum маппинг
        // ...
    )
}

// Domain → DTO (если нужно отправить на сервер)
fun VotingTypeDto.fromDomain(type: VotingType): VotingTypeDto {
    return when (type) {
        VotingType.SINGLE -> VotingTypeDto.SINGLE
        // ...
    }
}
```

## Dependency Injection (Hilt)

### NetworkModule
```kotlin
@Module @InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideRetrofitInstance(): Retrofit { ... }
    
    @Provides @Singleton
    fun provideRetrofitVotingsClient(): RetrofitVotingsClient { ... }
}
```

### RepositoryModule
```kotlin
@Module @InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindsVotingRepository(
        votingRepositoryImpl: VotingRepositoryImpl
    ): VotingRepository // binds implementation to interface
}
```

### UseCaseModule
```kotlin
@Module @InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides @Singleton
    fun provideGetVotingsUseCase(
        repository: VotingRepository // зависит от interface
    ): GetVotingsUseCase = GetVotingsUseCase(repository)
}
```

## Проверка Clean Architecture

✅ **Domain не зависит от Data:**
- Domain модели не содержат аннотаций Retrofit/serialization
- UseCases работают только с repository interface
- Нет импортов `data.api.*` в `domain/`

✅ **Data зависит от Domain:**
- RepositoryImpl реализует VotingRepository interface
- DTO конвертируются в Domain модели внутри repository
- ApiService возвращает DTO, не Domain модели

✅ **Presentation зависит от Domain:**
- ViewModel использует UseCases
- UI не знает о repository или API
- Нет импортов `data.*` в `presentation/`

## Архитектурные улучшения

### Что было исправлено:

1. **APIService теперь возвращает DTO вместо Domain моделей**
   - Раньше: `fun getVotings(): List<Voting>` ❌
   - Теперь: `fun getVotings(): VotingsResponseDto` ✅

2. **Создан слой маппинга**
   - `data/mapper/VotingMapper.kt` содержит все преобразования

3. **DTO изолированы от Domain**
   - Созданы `VotingTypeDto` и `VotingStatusDto` чтобы избежать импортов domain
   - DTO не экспонируются за пределы data слоя

4. **RepositoryImpl отвечает за маппинг**
   - Маппинг происходит в одном месте
   - Domain получает чистые модели

## Рекомендации

1. **Не импортируй DTO в Presentation или Domain**
   - Если видишь импорт `data.api.*` в других слоях — это нарушение

2. **Не импортируй Domain модели в Data кроме мапперов**
   - Маппинг должен быть явным и централизованным

3. **Каждый UseCase — одна операция**
   - Не создавай "супер-UseCase" который делает всё
   - Один UseCase = одна бизнес-операция

4. **Repository interface в Domain, реализация в Data**
   - Это позволяет тестировать Domain с моками

## Тестирование

### Unit тест UseCase:
```kotlin
class GetVotingsUseCaseTest {
    @Test
    fun `should return votings from repository`() = runTest {
        val mockRepository = MockVotingRepository()
        val useCase = GetVotingsUseCase(mockRepository)
        
        val result = useCase()
        
        assertThat(result).isNotEmpty()
    }
}
```

### Mock Repository:
```kotlin
class MockVotingRepository : VotingRepository {
    override suspend fun getVotings(): List<Voting> {
        return listOf(
            Voting(id = "1", title = "Test", ...)
        )
    }
    // ...
}
```

## Ссылки

- [Clean Architecture: A Tale of Two Decades](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Android Guide to Architecture](https://developer.android.com/topic/architecture)
- [Multiplatform App Architecture](https://developer.android.com/kotlin/multiplatform/architecture)
