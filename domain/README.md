# Couche Domain

## Role
La couche `domain` contient le coeur metier de l'application.
Elle decrit le probleme fonctionnel sans detail technique (API, UI, base locale, framework).

## Ce que cette couche contient
- modeles metier (`Location`, `Resident`, `LocationPage`, etc.)
- contrats metier (`LocationRepository`)
- cas d'usage (`GetLocationsUseCase`, `GetLocationDetailUseCase`, `GetLocationResidentsUseCase`)

## Regles importantes
- aucune dependance vers `data`, `presentation`, Android, Desktop, Ktor ou Compose
- logique metier pure, testable et reusable
- noms explicites orientes metier

## Dependances autorisees
- Kotlin standard
- coroutines si necessaire pour les contrats suspend

## Dependances interdites
- details d'infrastructure (HTTP, SQL, cache concret)
- details d'affichage (UiState, Compose, navigation)

## Flux attendu
`presentation` appelle un use case -> le use case utilise un contrat repository -> l'implementation concrete vit en `data`.

