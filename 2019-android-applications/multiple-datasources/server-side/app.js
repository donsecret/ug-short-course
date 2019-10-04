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
  var users = connection.query(
    "SELECT * from users",
    (err, rows, fields) => {
      
      if (err) {
        return [];
      } else {
        return rows;
      }
    }
  );
  // console.log(users);
  connection.end();
  return res.status(200).send(Flatted.stringify(users));
});

// Get user by id
app.post("/users/me", (req, res) => {
  // Get the user's id form the request
  console.log(req.body);

  if (req.body) {
    var id = req.body;

    return res.status(201).json({
      message: `Your id is: ${id}`
    });
  } else {
    return res.status(404).send({
      message: "Your request is invalid"
    });
  }
});

// Chats route
app.post("/chats", (req, res) => {});

// Add chat
app.post("/chats/new", (req, res) => {});

// Export module
module.exports = app;
