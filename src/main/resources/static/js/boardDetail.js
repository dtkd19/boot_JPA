console.log("board detail js in ~~~");

document.getElementById('listBtn').addEventListener('click', () => {
    location.href = "/board/list";
})

document.getElementById('modBtn').addEventListener('click' , () => {
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;

    // 버튼 생성
    let modBtn = document.createElement("button");
    modBtn.setAttribute('type','submit');
    modBtn.classList.add('btn', 'btn-outline-primary');
    modBtn.innerText="submit";

    // 추가 
    document.getElementById('modForm').appendChild(modBtn);
    document.getElementById("modBtn").remove();
    document.getElementById("delBtn").remove();

})