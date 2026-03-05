fetch("/issues/stats")
.then(res => res.json())
.then(data => {

createChart("categoryChart","Issue Categories",data.category)
createChart("priorityChart","Priority Distribution",data.priority)
createChart("statusChart","Issue Status",data.status)

/* TOP STATS */

let total = Object.values(data.status).reduce((a,b)=>a+b,0)

document.getElementById("totalIssues").innerText = total
document.getElementById("pendingIssues").innerText = data.status["Pending"] || 0
document.getElementById("resolvedIssues").innerText = data.status["Completed"] || 0
document.getElementById("highPriority").innerText = data.priority["High"] || 0


/* AI INSIGHT */

let maxCategory = Object.keys(data.category).reduce((a,b)=>
data.category[a]>data.category[b]?a:b)

document.getElementById("insightText").innerText =
"Most reported issues are related to "+maxCategory+
". Administration should prioritize maintenance resources in this area."

})


function createChart(id,title,data){

let labels = Object.keys(data)
let values = Object.values(data)

new Chart(document.getElementById(id),{

type:'bar',

data:{
labels:labels,

datasets:[{
label:title,
data:values,

backgroundColor:[
'#6366f1',
'#10b981',
'#f59e0b',
'#ef4444',
'#3b82f6'
]

}]
},

options:{
responsive:true,
plugins:{
legend:{display:false}
}

}

})

}