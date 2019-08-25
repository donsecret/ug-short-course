const express = require("express");
const app = express();

// Default route
app.get("/", (req, res) => {
  return res.json({
    message: "Connected to UG Scheduler successfully"
  });
});

app.listen(2019, () => {});

module.exports = app;
