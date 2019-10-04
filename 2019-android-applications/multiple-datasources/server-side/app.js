const app = require("express")();
const morgan = require("morgan");
const mysql = require("mysql");
const Flatted = require("flatted");

// Middlewares
app.use(morgan("dev"));

// MySQL Connection
var connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  database: "pied_piper"
});

// Connect to database
// connection.connect(err => {
//   if (err) {
//     return console.log(err.message);
//   } else {
//     console.log("Connected to database successfully");
//   }
// });

// Login route
app.post("/auth", (req, res) => {
  if (req.body) {
    // var phone = req.body.phone;
    var id = req.body.id;
    var name = req.body.name;
    var avatar = req.body.avatar;

    res.status(201).send({
      id,
      name,
      avatar
    });
  } else {
    res.status(401).send(null);
  }
});

// Get all users
app.get("/users", (req, res) => {
  connection.connect(err => {
    if (err) {
      return console.log(err.message);
    } else {
      console.log("Connected to pied_piper successfully");
    }
  });
  var users = connection.query("SELECT * from users", (err, rows, fields) => {
    if (err) {
      return [];
    } else {
      return rows;
    }
  });
  // console.log(users);
  connection.end();
  return res.status(200).send(Flatted.stringify(users));
});

// Get user by id
app.post("/users/:id", (req, res) => {
  // Get the user's id form the request
  var id = req.params.id;
  console.log(id);

  if (id) {
    connection.connect();
    connection.query(
      "select * from users where id = ? limit 1",
      [id],
      (err, rows, fields) => {
        if (rows[0] == null) {
          return res.status(404).send(null);
        }
        return res.status(200).send(rows[0]);
      }
    );
    connection.end();
  } else {
    return res.status(404).send({
      message: "Your request is invalid"
    });
  }
});

// Add multiple users
app.post("/users/new/multi", (req, res) => {
  if (req.body) {
    connection.connect();
    var query = "insert into user values(?,?,?,?)";
    req.body.forEach(user => {
      var params = [user.id, user.name, user.avatar, new Date().getTime()];
      connection.query(query, params, (err, rows, fields) => {
        if (err) {
          return res.status(400).send({
            message: err.message
          });
        }
        return res.status(200).send({
          message: "Fields inserted successfully"
        });
      });
    });
    connection.end();
  }
});

// Add chat
app.post("/chats/new", (req, res) => {
  if (req.body) {
    var chat = req.body;
    connection.connect();
    var query = "insert into chat values(?,?,?,?,?)";
    connection.query(
      query,
      [
        chat.id,
        chat.sender,
        chat.recipient,
        chat.message,
        new Date().getTime()
      ],
      (err, rows, fields) => {
        if (err) {
          return res.status(400).send({
            message: err.message
          });
        }
        return res.status(200).send({
          message: "Fields inserted successfully"
        });
      }
    );
    connection.end();
  }
});

// Add chat
app.post("/chats", (req, res) => {
  if (req.body) {
    var chat = req.body;
    connection.connect();
    var query = "select * from chat where sender = ? and recipient = ?";
    connection.query(
      query,
      [chat.sender, chat.recipient],
      (err, rows, fields) => {
        if (err) {
          return res.status(400).send([]);
        }
        return res.status(200).send(rows);
      }
    );
    connection.end();
  } else return res.send([]);
});

app.delete("/chats/:id", (req, res) => {
  var id = req.params.id;

  connection.connect();
  connection.query(
    "delete from chat where id = ?",
    [id],
    (err, rows, fields) => {
      if (err) {
        return res.status(400).send({
          message: `Unable to delete your chat with id: ${id}`
        });
      }
      return res.send({ message: "Chat delete successfully" });
    }
  );
  connection.end();
});

// Export module
module.exports = app;
