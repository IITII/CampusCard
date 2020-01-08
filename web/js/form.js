function schedule() {
    let schedule = document.getElementById('schedule').value;
    let form = document.getElementById('form');
    let context = document.getElementById('context').value;
    if (parseInt(schedule) === 1) {
        form.action = context + "/new_schedule.do";
    } else {
        form.action = context + "/cancel_schedule.do";
    }
}

function back() {
    let abs = document.getElementById('abs').value;
    let link = window.location.search.substring(1);
    let type = parseInt(document.getElementById('type').value);
    switch (type) {
        case 1:
            window.location.href = abs + "/Student/chaxunlishiliushui.jsp";
            break;
        case 2:
            window.location.href = abs + "/Admin/liushuitongji.jsp";
            break;
        case 3:
            window.location.href = abs + "/Teacher/liushuichaxun.jsp";
            break;
    }
}
function removeHidden(flag) {
    let add = document.getElementById('add');
    let update = document.getElementById('update');
    let remove = document.getElementById('remove');
    switch (flag) {
        case 1:
            add.removeAttribute('hidden');
            update.setAttribute('hidden','hidden');
            remove.setAttribute('hidden','hidden');
            break;
        case 2:
            update.removeAttribute('hidden');
            add.setAttribute('hidden','hidden');
            remove.setAttribute('hidden','hidden');
            break;
        case 3:
            remove.removeAttribute('hidden');
            add.setAttribute('hidden','hidden');
            update.setAttribute('hidden','hidden');
            break;
    }
}
