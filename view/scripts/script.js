const tasksEndpoint = "http://localhost:8080/task/user";

function hideLoader() {
  document.getElementById("loading").style.display = "none";
}
function diplayName(){
  if (localStorage.getItem("username")) {
        const username = localStorage.getItem('username');
        document.getElementById('tasks-header').textContent = `Tarefas de ${username}`;
      }
}
function show(tasks) {
  diplayName()
  let tab = `<thead>
            <th scope="col">Tarefa</th>
            <th scope="col">Descrição</th>
        </thead>`;

  for (let task of tasks) {
    tab += `
            <tr>
                <td>${task.id}</td>
                <td>${task.description}</td>
            </tr>
        `;
  }

  document.getElementById("tasks").innerHTML = tab;
}

async function getTasks() {
  let key = "Authorization";
  const response = await fetch(tasksEndpoint, {
    method: "GET",
    headers: new Headers({
      Authorization: localStorage.getItem(key),
    }),
  });

  var data = await response.json();
  console.log(data);
  if (response) hideLoader();
  show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
  if (!localStorage.getItem("Authorization"))
    window.location = "login.html";
});

getTasks();
