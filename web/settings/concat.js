const concat = require("concat");
(async function build() {
  const files = [
    "./dist/settings/runtime.js",
    "./dist/settings/polyfills.js",
    "./dist/settings/main.js",
  ];
  await concat(files, "./dist/settings/settings-mfe.js");
})();
