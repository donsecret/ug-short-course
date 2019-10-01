const app = require("express")();
const morgan = require("morgan");

// Middlewares
app.use(morgan("dev"));

// Login route
app.post("/auth", (req, res) => {});

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
