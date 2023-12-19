
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

let menuActiveArr = []; //활성 팝업객체 저장
let isPopupOpend = [];  //활성 팝업여부 표시

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
            
            //
            const submenuUrl =  item.getAttribute('data-submenu');
            const submenuIdx = item.getAttribute('data-idx');
            if(submenuUrl.includes("02")||submenuUrl.includes("03")||submenuUrl.includes("04")||submenuUrl.includes("06"))
            {
              //팝업창 가운데로 맞추기
              var popupWidth = 800;
              var popupHeight = 600;
              var popupX = (window.screen.width / 2) - (popupWidth / 2);
              var popupY = (window.screen.height / 2) - (popupHeight / 3);
              //팝업창 활성화
              menuActiveArr[submenuIdx] =  window.open(submenuUrl, '', 'status=no, height=' + popupHeight  + ', width=' + popupWidth  + ', left='+ popupX + ', top='+ popupY);
              isPopupOpend[submenuIdx] = true;
              console.log(submenuIdx,menuActiveArr[submenuIdx]);
            }
            else if(submenuUrl.includes("01")||submenuUrl.includes("05"))
            {
              
              //OFFCANVAS 버튼 오른쪽이동
              const offcanvas_btn_el = document.querySelector('.offcanvas_btn');
              if(!offcanvas_btn_el.classList.contains('ToRight')){
                  //OFFCANVAS BTN Move To RIGHT
                  offcanvas_btn_el.classList.add("ToRight");
              }

              //OFFCANVAS SHOW
              const myOffcanvas = document.querySelector('.offcanvas')
              let bsOffcanvas = new bootstrap.Offcanvas(myOffcanvas)
              bsOffcanvas.show();

              //01MENU 클릭시 오픈캔버스에 내용표시 
              if(submenuUrl.includes("01")){
               
                const buildingWindEl = document.querySelector('.buildingwind');
                buildingWindEl.style.display='block';
                buildingWindEl.style.opacity='1';
                //--------------------------
                //Canvas buildingwind X BUTTON EVENT
                //--------------------------
//                  offCanvasCancelEl = document.querySelector('.buildingwind>.header>.cancel a')
//                  offCanvasCancelEl.addEventListener('click',function(){
//                  const buildingWindEl = document.querySelector('.buildingwind');
//                  buildingWindEl.style.display="none";
//                })

               


              }
              //05MENU 클릭시 오픈캔버스에 내용표시 
              if(submenuUrl.includes("05")){
               
                const buildingWindEl = document.querySelector('.dangerzone');
                buildingWindEl.style.display='block';

                //--------------------------
                //Canvas dangerzone X BUTTON EVENT
                //--------------------------
                  offCanvasDangerCancelEl = document.querySelector('.dangerzone>.header>.cancel a')
                  offCanvasDangerCancelEl.addEventListener('click',function(){
                    DangerCancelEl = document.querySelector('.dangerzone');
                    DangerCancelEl.style.display="none"; 
                })
              }

            }
           

        }
        else
        {
            //IMAGE CHANGE
            item.setAttribute("data-toggle","off");
            const imgEl = item.firstElementChild;
            let str = imgEl.getAttribute('src');
            str = str.substring(0,str.indexOf('.'))+"_off.png";
            imgEl.setAttribute('src',str);

            //ALERTPOPUP WINDOW CLOSE
            const submenuIdx = item.getAttribute('data-idx');
            const submenuUrl =  item.getAttribute('data-submenu');

            if(submenuUrl.includes("02")||submenuUrl.includes("03")||submenuUrl.includes("04")||submenuUrl.includes("06"))
            {
              menuActiveArr[submenuIdx].close();
            }
            else if(submenuUrl.includes("01")||submenuUrl.includes("05"))
            {

             
              //팝업여부배열 false 
              isPopupOpend[submenuIdx] = false;
            
                //OFFCANVAS BTN 비활성화
                const offcanvas_btn_el = document.querySelector('.offcanvas_btn');
                if(offcanvas_btn_el.classList.contains('ToRight')){
                  //OFFCANVAS Move To RIGHT
                  offcanvas_btn_el.classList.remove("ToRight");
       
                }
                //OFFCANVAS 숨기기
                const myOffcanvas = document.querySelector('.offcanvas')
                myOffcanvas.classList.remove('show');
                const bsOffcanvas = new bootstrap.Offcanvas(myOffcanvas)
                bsOffcanvas.hide();
             

                
              //01MENU 클릭시 오픈캔버스에 제거
              if(submenuUrl.includes("01")){
               
                const buildingWindEl = document.querySelector('.buildingwind');
                buildingWindEl.style.display='none';

              }
              //01MENU 클릭시 오픈캔버스에 제거
              if(submenuUrl.includes("05")){
               
                const buildingWindEl = document.querySelector('.dangerzone');
                buildingWindEl.style.display='none';

              }

            }
           
            
        }

    })
    
})

//--------------------------
// POP Close Event Function ( X 버튼 눌렀을때 부모창 메뉴 스타일변경)
//--------------------------
 // 부모 창에서 메시지를 받는 이벤트 리스너 등록
 window.addEventListener('message', function (event) {
    // event.data에 자식 창에서 전달한 데이터가 들어 있음
    const receivedMessage = event.data;
    //console.log('자식 창으로부터 받은 메시지:', receivedMessage);
  
    //
    const nav_menu_img_items = document.querySelectorAll('nav li>a');
    nav_menu_img_items.forEach(item => {
      const submenuUrl =  item.getAttribute('data-submenu');
      if(submenuUrl.includes(receivedMessage)){
        item.setAttribute("data-toggle","off");
        const imgEl = item.firstElementChild;
        let str = imgEl.getAttribute('src');
        if(!str.includes('_off'))
          str = str.substring(0,str.indexOf('.'))+"_off.png";
        imgEl.setAttribute('src',str);
      }
      
    });


});





//--------------------------
// MAP CODE  + SKYVIEW
//--------------------------
	// Leaflet 초기화

const LCTlatlng = [35.16073, 129.1688];
const MARINElatlng = [35.15541, 129.1460];
const CENTUMPARKlatlan = [35.17899, 129.1227];




//  /var map = L.map('map').setView([35.166755,129.150615], 14); //가오비산 봉수대

var map = L.map('map').setView([35.16073, 129.1688], 15); //LCT ,14기본값
//-----------
//기본지도
//-----------
L.tileLayer('https://tiles.osm.kr/hot/{z}/{x}/{y}.png', {
		maxZoom: 19,
		attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);
//-----------
//위성지도
//-----------


// 다중 마커를 추가하고 각 마커에 팝업 설정
var markers = [
		{latlng: LCTlatlng, popupContent: 'LCT'},
		{latlng: MARINElatlng, popupContent: '마린시티'},
        {latlng: CENTUMPARKlatlan, popupContent: '센텀파크'},
	];


	markers.forEach(function(markerInfo) {
		var marker = L.marker(markerInfo.latlng).addTo(map);


    // 빨간색 마커 추가
    var redMarker = L.marker(markerInfo.latlng, {
      icon: L.icon({
          iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
          shadowUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-shadow.png',
          iconSize: [25, 41],
          iconAnchor: [12, 41],
          popupAnchor: [1, -34],
          shadowSize: [41, 41]
      }, )
    }).addTo(map);


    redMarker.bindPopup(markerInfo.popupContent);


});

// 포인터를 위한 사용자 지정 아이콘 정의
var pointerIcon = L.icon({
  iconUrl: '/images/pointer.png',
  iconSize: [8, 8], // 아이콘 크기 설정
  iconAnchor: [16, 32], // 아이콘의 앵커 지점 설정
  popupAnchor: [0, -32] // 팝업의 앵커 지점 설정
});



// //클릭한 위치의 정보 업데이트 함수(아직구현 x)
// function updateInfoPanel(latlng) {
// 		// 클릭한 위치의 위도와 경도 업데이트
// 		document.getElementById('latitude').textContent = latlng.lat.toFixed(6);
// 		document.getElementById('longitude').textContent = latlng.lng.toFixed(6);
//
// 		// 클릭한 위치의 주소를 지오코딩을 통해 가져오기(나중에)
// 		fetch('https://api.opencagedata.com/geocode/v1/json?q=' + latlng.lat + '+' + latlng.lng + '&key=YOUR_OPENCAGE_API_KEY')
// 			.then(response => response.json())
// 			.then(data => {
// 				const address = data.results[0].formatted;
// 				document.getElementById('address').textContent = address;
//         console.log('address',address);
// 			})
// 			.catch(error => {
// 				console.error('Error fetching address:', error);
// 			});
// 	}



//LCT 영역표시(GREEN)
var polygonPoints = [];
const createLCTSection =() =>{

     // 경계를 이용해 다각형을 만들고 원하는 색상을 설정합니다.
     var polygon = L.polygon(polygonPoints, { color: '', fillColor: 'green', fillOpacity: 0.3 }).addTo(map);

     // 다른 영역에 대해서도 동일한 방식으로 처리할 수 있습니다.
     var polygon2Points = [];
     var polygon2 = L.polygon(polygon2Points, { color: 'blue', fillColor: 'blue', fillOpacity: 0.5 }).addTo(map);

     // 또 다른 영역에 대해서도 동일한 방식으로 처리할 수 있습니다.
     var polygon3Points = [// 다른 영역의 좌표
     ];
     var polygon3 = L.polygon(polygon3Points, { color: 'green', fillColor: 'red', fillOpacity: 0.5 }).addTo(map);
     //영역 고정
    //map.setView([35.16061302338314 ,  129.1687917709351],15);

}




//------------------------------
// 지도 클릭 이벤트 리스너
//------------------------------
map.on('click', function (e) {
     console.log(e.target);
        //클릭한 지점의 위도 경도 표시
        console.log('lat :' ,e.latlng.lat,'lan : ',e.latlng.lng);
        polygonPoints.unshift([e.latlng.lat,e.latlng.lng]);
        console.log(polygonPoints);

        // 사용자 지정 아이콘을 사용하여 사용자 지정 마커 생성
        var customMarker = L.marker(e.latlng, { icon: pointerIcon }).addTo(map);



        //좌표점이 10개이상 채워지면 지도에 영역표시하기..
        if(polygonPoints.length>5){
            createLCTSection();
            polygonPoints = [];
        }

// 	/updateInfoPanel(e.latlng);
});

