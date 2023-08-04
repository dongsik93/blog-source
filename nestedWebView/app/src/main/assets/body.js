var editables = document.querySelectorAll('#content');

if (editables !== null) {
    bodySelector = editables[0];
}

function setBodyContent(body_content) {
    bodySelector.innerHTML = body_content;
}