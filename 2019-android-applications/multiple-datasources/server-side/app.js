const app = require("express")();
const morgan = require("morgan");

// Middlewares
app.use(morgan("dev"));

// Login route
app.post("/auth", (req, res) => {
    
});

// Chats route
app.post("/chats", (req, res) => {});

// Add chat
app.post("/chats/new", (req, res) => {});

// Export module
module.exports = app;
