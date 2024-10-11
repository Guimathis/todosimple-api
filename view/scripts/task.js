async function createtask() {
  let description = document.getElementById("description").value;

  console.log(description);

  const response = await fetch("http://localhost:8080/task", {
    method: "POST",
    headers: new Headers({
      "Content-Type": "application/json; charset=utf8",
      Accept: "application/json",
    }),
    body: JSON.stringify({
      description: description,
    }),
  });

  if (response.ok) {
    showToast("#okToast");
  } else {
    showToast("#errorToast");
  }
}

function showToast(id) {
  var toastElList = [].slice.call(document.querySelectorAll(id));
  var toastList = toastElList.map(function (toastEl) {
    return new bootstrap.Toast(toastEl);
  });
  toastList.forEach((toast) => toast.show());
}