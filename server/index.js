const express = require('express');
const bodyParser = require('body-parser');
const sqlite3 = require('sqlite3').verbose();
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());

// DB setup
const dbFile = path.join(__dirname, 'data.db');
const db = new sqlite3.Database(dbFile);

db.serialize(() => {
  db.run(`CREATE TABLE IF NOT EXISTS usuario_instagram (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_dispositivo TEXT NOT NULL,
    id_usuario_instagram TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
  );`);
});

// Endpoint /registrar-usuario
app.post('/registrar-usuario', (req, res) => {
  const { id_dispositivo, id_usuario_instagram } = req.body;
  if (!id_dispositivo || !id_usuario_instagram) {
    return res.status(400).json({ error: 'Parámetros requeridos: id_dispositivo, id_usuario_instagram' });
  }

  const stmt = db.prepare('INSERT INTO usuario_instagram (id_dispositivo, id_usuario_instagram) VALUES (?, ?)');
  stmt.run(id_dispositivo, id_usuario_instagram, function(err) {
    if (err) {
      console.error(err);
      return res.status(500).json({ error: 'Error al insertar en la base de datos' });
    }
    res.json({ success: true, id: this.lastID });
  });
  stmt.finalize();
});

app.get('/', (req, res) => res.send('Petagram FCM Server running'));

app.listen(PORT, () => console.log(`Server listening on port ${PORT}`));
