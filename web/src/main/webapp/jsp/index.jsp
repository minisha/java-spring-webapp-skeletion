<html>
<body>
<h2>Test page</h2>
<button type="button" onclick="getDocuments()">Click Me!</button>
<div id="id-div"></div>
</body>
<script>
function getDocuments() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("id-div").innerHTML = xhttp.responseText;
    }
  };
  xhttp.open("GET", "ajax/getDocument", true);
  xhttp.send();
}
</script>
</html>
