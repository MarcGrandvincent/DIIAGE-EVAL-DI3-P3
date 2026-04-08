# Couche Data

## Role
La couche `data` fournit les implementations concretes des contrats du `domain`.
Elle recupere, transforme et stocke les donnees.

## Ce que cette couche contient
- clients remote (API Rick and Morty)
- source locale (cache memoire ou persistance)
- DTO / Entity
- mappers (DTO -> Domain, Entity -> Domain)
- implementation de `LocationRepository`

## Strategie de fetch
- remote-first: tentative API en priorite
- fallback local: utilisation du cache en cas d'echec remote
- retour en `Result` pour exposer succes/erreur proprement

## Regles importantes
- `data` peut dependre de `domain`
- `data` ne depend pas de `presentation`
- tous les details techniques restent confines ici

## Dependances typiques
- Ktor (network)
- Kotlinx Serialization
- mecanisme de stockage local

## Flux attendu
Source remote/local -> mapping -> modeles du `domain` -> retour via repository vers les use cases.

