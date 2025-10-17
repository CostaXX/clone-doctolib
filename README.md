il faut avoir installer :

- docker
- maven
- java

pour lancer ce projet il vous suffit de lancer le conteneur docker avec

compose up -d --wait

🔹 Rafraîchissement du token

Le client envoie une requête spéciale avec le refresh token :

POST /auth/refresh
Content-Type: application/json

{
  "refresh_token": "<refresh_token>"
}


Le serveur :

Vérifie que le refresh token est valide et non expiré.

Émet un nouveau access token (et souvent un nouveau refresh token).

Réponse :

{
  "access_token": "nouveau_access_token",
  "refresh_token": "nouveau_refresh_token"
}