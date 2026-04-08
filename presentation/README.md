# Couche Presentation

## Role
La couche `presentation` transforme les besoins utilisateur en actions metier et affiche les etats UI.
Elle suit un flux UDF/MVI pour garder un comportement previsible.

## Ce que cette couche contient
- ecrans Compose (`LocationList`, `LocationDetail`, variante Desktop)
- `UiState`, `UiAction`, event UI
- ViewModels (pilotage des use cases)
- navigation centralisee
- theme et composants UI reutilisables

## Regles importantes
- `presentation` depend de `domain` (et jamais l'inverse)
- aucune logique metier complexe dans les composables
- les composables rendent l'etat, les ViewModels orchestrent les actions

## Flux UDF/MVI
Action utilisateur -> ViewModel -> UseCase (`domain`) -> nouveau `UiState` -> recomposition UI.

## Adaptation multi-plateforme
- mobile: navigation en ecrans (liste -> detail)
- desktop: ecran master-detail
- logique partagee en `commonMain`, points d'entree natifs dans les modules app

