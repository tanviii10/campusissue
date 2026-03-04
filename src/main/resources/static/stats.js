fetch("/issues/stats")
.then(res => res.json())
.then(data => {

document.getElementById("stats").innerHTML = `

<h3>Total Issues: ${data.totalIssues}</h3>
<h3>Pending Issues: ${data.pendingIssues}</h3>
<h3>Resolved Issues: ${data.resolvedIssues}</h3>
<h3>High Priority Issues: ${data.highPriorityIssues}</h3>
<h3>Most Common Category: ${data.mostCommonCategory}</h3>

`;

});