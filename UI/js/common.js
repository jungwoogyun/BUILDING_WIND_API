
//--------------------------
// OPENCANVAS
//--------------------------
const offcanvas_btn_el = document.querySelector('.offcanvas_btn');
offcanvas_btn_el.addEventListener('click', function(){

    console.log("btn..");
    if(offcanvas_btn_el.classList.contains('ToRight'))
        offcanvas_btn_el.classList.remove("ToRight");
    else
        offcanvas_btn_el.classList.add("ToRight");
})
//--------------------------
// Nav li Click Event
//--------------------------

const nav_menu_img_items = document.querySelectorAll('nav li>a');

nav_menu_img_items.forEach(item =>{
    
   
    item.addEventListener('click',function(){
        const isOpened =  item.getAttribute('data-toggle');
        
        console.log("!!" ,isOpened);
        if(isOpened==="off"){
            
            //IMAGE CHANGE
            item.setAttribute("data-toggle","on");
            const imgEl = item.firstElementChild;
            let str = imgEl.getAttribute('src');
            str = str.substring(0,str.indexOf('_'))+".png";
            imgEl.setAttribute('src',str);
            //MODAL WINDOW OR.. WINDOW.OPEN90

        }else{
            //IMAGE CHANGE
            item.setAttribute("data-toggle","off");
            const imgEl = item.firstElementChild;
            let str = imgEl.getAttribute('src');
            str = str.substring(0,str.indexOf('.'))+"_off.png";
            imgEl.setAttribute('src',str);

        }

    })
    
})

//--------------------------
// 모달 드래그 가능하게 만들기(테스트)
//--------------------------
var isMouseDown = false;
var offsetX, offsetY;

// 모달 드래그 시작
document.querySelector('.draggable').addEventListener('mousedown', function (e) {
  e.preventDefault();
  isMouseDown = true;
  offsetX = e.offsetX;
  offsetY = e.offsetY;
});

// 모달 드래그 종료
document.addEventListener('mouseup', function () {
  isMouseDown = false;
});

// 모달 드래그 중
document.addEventListener('mousemove', function (e) {
e.preventDefault();
  if (isMouseDown) {
    var modal = document.querySelector('.draggable');
    var modalLeft = modal.offsetLeft;
    var modalTop = modal.offsetTop;
    modal.style.left = '100px';
    modal.style.top = '100px';
  }
});


//--------------------------
// MAP CODE
//--------------------------
