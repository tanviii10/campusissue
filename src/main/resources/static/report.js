function submitIssue(){

let description=document.getElementById("description").value;
let severity=document.getElementById("severity").value;
let location=document.getElementById("location").value;

fetch("/issues/create?description="+encodeURIComponent(description)
+"&severity="+severity
+"&location="+location)

.then(res=>res.json())
.then(data=>{

document.getElementById("result").innerHTML=

"<h3>Prediction Result</h3>"+
"Category: "+data.category+"<br>"+
"Priority: "+data.priority+"<br>"+
"Resolution Time: "+data.predictedResolutionTimeHours+" hours";

});

}