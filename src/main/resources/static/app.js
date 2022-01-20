const contentArea = document.querySelector("#content");
const contentLength = document.querySelector(".content-length");
const back = document.querySelector("#back");
const modal = document.querySelector(".modal");
const buttons = modal.querySelectorAll("button");

function handleContentArea(element) {
    let contentLength = String(element.value).length;

    contentLength = handleModal(contentLength);

    displayLength(contentLength);
}

buttons.forEach((element) => {
    element.addEventListener("click", modalOff);
})

function modalOn(length) {
    modal.className = "visible";
    back.className = "blind";

    contentArea.disabled = true;
    contentArea.value = String(contentArea.value).substring(0, 300);

    return contentArea.value.length;
}

function modalOff(length) {
    modal.className = "modal";
    back.className = "none";

    contentArea.disabled = false;

    return length;
}

function handleModal(length) {
    return (length > 300) ? modalOn(length) : length;
}

function displayLength(length) {
    contentLength.innerText = `(${length} / 300)`;
}