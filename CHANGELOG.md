## 2026-05-21 (Clean Architecture)
- [client] В `domain` добавлен порт `VotingRepository` и use case `GetVotingsUseCase`.
- [client] HTTP-логика переведена в `data` слой через `VotingRepositoryImpl` (адаптер на базе `ApiService/KtorApiService`).
- [client] `FeedViewModel` теперь зависит только от use case (а не от HTTP/API).
- [client] Hilt `AppModule` бинит `VotingRepository` и `GetVotingsUseCase`.

