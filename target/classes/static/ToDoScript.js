const todos = document.querySelectorAll(".todo");
const all_status = document.querySelectorAll(".status");
let draggableTodo = null;

todos.forEach((todo) => {
	todo.addEventListener("dragstart", dragStart);
	todo.addEventListener("dragend", dragEnd);
});

function dragStart() {
	draggableTodo = this;
}

function dragEnd() {
	draggableTodo = null;
}

all_status.forEach((status) => {
	status.addEventListener("dragover", dragOver);
	status.addEventListener("dragenter", dragEnter);
	status.addEventListener("dragleave", dragLeave);
	status.addEventListener("drop", dragDrop);
});

function dragOver(e) {
	//by default, dragging into an element is disabled
	e.preventDefault();
}

function dragEnter() {
	this.style.border = '1px dashed #ccc';
}

function dragLeave() {
	this.style.border = "none";
}

function dragDrop() {
	this.style.border = "none";
	this.appendChild(draggableTodo);
	console.log(this.id);

}

