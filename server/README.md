Petagram FCM Server (Node.js + SQLite)

Instrucciones:
1. `cd server`
2. `npm install`
3. `node index.js`
4. El endpoint estará en http://localhost:3000/registrar-usuario

Para desplegar en Heroku:
- `heroku create tu-app-name`
- `git init` (si aún no)
- `git add .` `git commit -m "initial"`
- `git push heroku main`
- `heroku open`

Nota: Heroku filesystem es efímero. Para persistencia real en producción use Postgres o un servicio de DB gestionado.
