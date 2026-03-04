function submitIssue(){

    let description = document.getElementById("description").value;
    let severity = document.getElementById("severity").value;

    let url = "http://localhost:8080/issues/create?description=" 
                + encodeURIComponent(description) 
                + "&severity=" + severity;

    fetch(url)
    .then(response => response.json())
    .then(data => {

        document.getElementById("result").innerHTML = `
        <b>Category:</b> ${data.category} <br>
        <b>Priority:</b> ${data.priority} <br>
        <b>Estimated Resolution Time:</b> ${data.predictedResolutionTimeHours} hours
        `;

    })
}