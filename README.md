# rso-scores
Microservice for scores

```bash
docker run -d --name pg-scores -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=scores -p 5432:5432 postgres:10.5
```