const app = require("express")();

// Default route
app.get("/", (req, res) => {});

// todo: Auth

// todo: Schedules

// todo: Courses

// todo: Search

// todo: Users

// Start server on port
app.listen(2019, () => {
  console.log("Server started on port 2019");
});
