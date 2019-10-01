// Get Express app instance
const app = require("./app");

// Configure port number
var port = process.env.PORT || 3000;

// Start server
app.listen(port, () => console.log(`Server started on port: ${port}`));
