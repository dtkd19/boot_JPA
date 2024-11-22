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
    modBtn.setAttribute('id','regBtn');
    modBtn.classList.add('btn', 'btn-outline-primary');
    modBtn.innerText="submit";

    // 추가 
    document.getElementById('modForm').appendChild(modBtn);
    document.getElementById("modBtn").remove();
    document.getElementById("delBtn").remove();

    let fileDelBtn = document.querySelectorAll(".file-x");
    console.log(fileDelBtn);
    for(let delBtn of fileDelBtn){
        delBtn.disabled = false;
    };

    document.getElementById('trigger').disabled = false;


})
document.getElementById('delBtn').addEventListener('click', () => {
    let bnoVal = document.getElementById('n').value;
    location.href=`/board/delete?bno=${bnoVal}`;
});


document.addEventListener('click', (e) => {
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid;
        console.log(uuid);
        let li = e.target.closest('li');

        removeFileToServer(uuid).then(result => {
            if(result == "1"){
                li.remove();
                alert("파일삭제 성공!!");
            } else{
                alert("파일삭제 실패!!");
            }
        })
    }
})

async function removeFileToServer(uuid) {
    try {
        const url = ("/board/file/"+ uuid)
        const config = {
            method : 'delete'
        }
        const resp = await fetch(url, config); 
        const result = await resp.text();
        return result;

    } catch (error) {
        console.log(error);
    }
}