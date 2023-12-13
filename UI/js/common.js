
// OPENCANVAS

const offcanvas_btn_el = document.querySelector('.offcanvas_btn');
offcanvas_btn_el.addEventListener('click', function(){

    console.log("btn..");
    if(offcanvas_btn_el.classList.contains('ToRight'))
        offcanvas_btn_el.classList.remove("ToRight");
    else
        offcanvas_btn_el.classList.add("ToRight");
})