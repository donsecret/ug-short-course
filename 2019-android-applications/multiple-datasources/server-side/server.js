// Get Express app instance
const app = require("./app");
const mysql = require("mysql");

// Configure port number
var port = process.env.PORT || 3000;

// Start server
app.listen(port, () => {
  console.log(`Server started on port: ${port}`);
  mysql
    .createConnection({
      host: "localhost",
      user: "root",
      database: "pied_piper"
    })
    .connect(err => {
      if (err) {
        return console.log(err.message);
      }
    });
});
