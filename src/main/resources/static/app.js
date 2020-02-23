var ws;
var priorities = ['LOW', 'MEDIUM', 'HIGH'];
var statuses = ['NEW', 'OPEN', 'CLOSED', 'IN_PROGRESS'];

function onPageLoad() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            renderTable(this.responseText);
            connect();
        }
    };
    xhttp.open("GET", "http://localhost:8080/omnius-event-store/task", true);
    xhttp.send();
}

function refreshData() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            renderTable(this.responseText);
        }
    };
    xhttp.open("GET", "http://localhost:8080/omnius-event-store/task", true);
    xhttp.send();
}

function connect() {
    ws = new WebSocket('ws://localhost:8080/register');
    ws.onmessage = function (data) {
        renderTable(data.data);
    }
}

function renderTable(data) {
    var new_tbody = document.createElement('tbody');
    var old_tbody = document.getElementById('conversation').childNodes[3];
    populate_with_new_rows(JSON.parse(data), new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)
}

function cancel(activeRow) {

    // HIDE THIS BUTTON.
    document.getElementById('lbl' + activeRow).setAttribute('style', 'display:none;')

    // HIDE THE SAVE BUTTON.
    var btSave = document.getElementById('Save' + (activeRow));
    btSave.setAttribute('style', 'display:none;');

    // SHOW THE UPDATE BUTTON AGAIN.
    var btUpdate = document.getElementById('Edit' + (activeRow));
    btUpdate.setAttribute('style', 'display:block; margin:0 auto; background-color:#44CCEB;');
    var btDelete = document.getElementById('Delete' + activeRow);
    btDelete.setAttribute('style', 'display:block; margin:0 auto; background-color:#44CCEB;');
    var tab = document.getElementById('conversation').rows[activeRow];
    restoreRow(tab);
}

function deleteTask(activeRow) {
    var tab = document.getElementById('conversation').rows[activeRow];
    var taskId = tab.cells[1].innerText;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 202) {
            refreshData();
        }
    };
    xhttp.open("DELETE", "http://localhost:8080/omnius-event-store/task/" + taskId, true);
    xhttp.send(null);
}

function restoreRow(tab) {
    tab.cells[2].innerHTML = tab.cells[2].children[0].value;
    tab.cells[3].innerHTML = tab.cells[3].children[0].value;
    tab.cells[4].innerHTML = tab.cells[4].children[0].value;
    tab.cells[5].innerHTML = tab.cells[5].children[0].value;
    tab.cells[6].innerHTML = tab.cells[6].children[0].value;
}


function save(activeRow) {
    var tab = document.getElementById('conversation').rows[activeRow];
    restoreRow(tab);
    var taskId = tab.cells[1].innerText;
    var title = tab.cells[2].innerText;
    var description = tab.cells[3].innerText;
    var status = tab.cells[5].innerText;
    var priority = tab.cells[4].innerText;
    var dueDate = tab.cells[6].innerText;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            refreshData();
        }
    };
    task = {
        "id": taskId,
        "title": title,
        "description": description,
        "status": status,
        "priority": priority,
        "dueDate": dueDate
    };
    xhttp.open("PUT", "http://localhost:8080/omnius-event-store/task/" + taskId, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(task));
}

function update(activeRow) {
    var tab = document.getElementById('conversation').rows[activeRow];

    // SHOW A DROPDOWN LIST WITH A LIST OF CATEGORIES.
    for (i = 2; i <= 6; i++) {
        if (i == 4) {
            var td = tab.getElementsByTagName("td")[i];
            var ele = document.createElement('select');      // DROPDOWN LIST.
            ele.innerHTML = '<option value="' + td.innerText + '">' + td.innerText + '</option>';
            for (k = 0; k < this.priorities.length; k++) {
                if (this.priorities[k] != td.innerText) {
                    ele.innerHTML = ele.innerHTML +
                        '<option value="' + this.priorities[k] + '">' + this.priorities[k] + '</option>';
                }
            }
            td.innerText = '';
            td.appendChild(ele);
        } else if (i == 5) {
            var td = tab.getElementsByTagName("td")[i];
            var ele = document.createElement('select');      // DROPDOWN LIST.
            ele.innerHTML = '<option value="' + td.innerText + '">' + td.innerText + '</option>';
            for (k = 0; k < this.statuses.length; k++) {
                if (this.statuses[k] != td.innerText) {
                    ele.innerHTML = ele.innerHTML +
                        '<option value="' + this.statuses[k] + '">' + this.statuses[k] + '</option>';
                }
            }
            td.innerText = '';
            td.appendChild(ele);
        } else {
            var td = tab.getElementsByTagName("td")[i];
            var ele = document.createElement('input');      // TEXTBOX.
            ele.setAttribute('type', 'text');
            ele.setAttribute('value', td.innerText);
            td.innerText = '';
            td.appendChild(ele);
        }
    }

    var lblCancel = document.getElementById('lbl' + (activeRow));
    lblCancel.setAttribute('style', 'cursor:pointer; display:block; width:20px; float:left; position: absolute;');

    var btSave = document.getElementById('Save' + (activeRow));
    btSave.setAttribute('style', 'display:block; margin-left:30px; float:left; background-color:#2DBF64;');

    // HIDE THIS BUTTON.
    document.getElementById('Edit' + activeRow).setAttribute('style', 'display:none;')
    document.getElementById('Delete' + activeRow).setAttribute('style', 'display:none;')
    //oButton.setAttribute('style', 'display:none;');
};


function populate_with_new_rows(tasks, tableRef) {
    for (var index = 0; index < tasks.length; index++) {
        var newRow = tableRef.insertRow();
        var newCell = newRow.insertCell(0);
        var newText = document.createTextNode(index + 1);
        newCell.appendChild(newText);

        newCell = newRow.insertCell(1);
        newText = document.createTextNode(tasks[index].id);
        newCell.appendChild(newText);

        newCell = newRow.insertCell(2);
        newText = document.createTextNode(tasks[index].title);
        newCell.appendChild(newText);

        newCell = newRow.insertCell(3);
        newText = document.createTextNode(tasks[index].description);
        newCell.appendChild(newText);

        newCell = newRow.insertCell(4);
        newText = document.createTextNode(tasks[index].priority);
        newCell.appendChild(newText);

        newCell = newRow.insertCell(5);
        newText = document.createTextNode(tasks[index].status);
        newCell.appendChild(newText);

        newCell = newRow.insertCell(6);
        newText = document.createTextNode(tasks[index].dueDate);
        newCell.appendChild(newText);


        newCell = newRow.insertCell(7);

        // *** CANCEL OPTION.
        var lblCancel = document.createElement('label');
        lblCancel.innerHTML = '✖';
        lblCancel.setAttribute('onclick', "cancel(" + (index + 1) + ")");
        lblCancel.setAttribute('style', 'display:none;');
        lblCancel.setAttribute('title', 'Cancel');
        lblCancel.setAttribute('id', 'lbl' + (index + 1));
        newCell.appendChild(lblCancel);

        // *** SAVE.
        var btSave = document.createElement('input');

        btSave.setAttribute('type', 'button');      // SET ATTRIBUTES.
        btSave.setAttribute('value', 'Save');
        btSave.setAttribute('id', 'Save' + (index + 1));
        btSave.setAttribute('style', 'display:none;');
        btSave.setAttribute('onclick', "save(" + (index + 1) + ")");       // ADD THE BUTTON's 'onclick' EVENT.
        newCell.appendChild(btSave);

        // *** SAVE.
        var btDelete = document.createElement('input');

        btDelete.setAttribute('type', 'button');      // SET ATTRIBUTES.
        btDelete.setAttribute('value', 'Delete');
        btDelete.setAttribute('id', 'Delete' + (index + 1));
        btDelete.setAttribute('style', 'background-color:#44CCEB;');
        btDelete.setAttribute('onclick', "deleteTask(" + (index + 1) + ")");       // ADD THE BUTTON's 'onclick' EVENT.
        newCell.appendChild(btDelete);

        // *** UPDATE.
        var btUpdate = document.createElement('input');

        btUpdate.setAttribute('type', 'button');    // SET ATTRIBUTES.
        btUpdate.setAttribute('value', 'Update');
        btUpdate.setAttribute('id', 'Edit' + (index + 1));
        btUpdate.setAttribute('style', 'background-color:#44CCEB;');
        btUpdate.setAttribute('onclick', "update(" + (index + 1) + ")");   // ADD THE BUTTON's 'onclick' EVENT.
        newCell.appendChild(btUpdate);
    }
}

