
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
	// Leaflet 초기화
	var map = L.map('map').setView([37.5729, 126.9794], 15);

  L.tileLayer('https://tiles.osm.kr/hot/{z}/{x}/{y}.png', {
		maxZoom: 19,
		attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap</a>'
	}).addTo(map);

  	// 다중 마커를 추가하고 각 마커에 팝업 설정
	var markers = [
		{latlng: [37.5729, 126.9794], popupContent: '서울특별시 종로구'},
		{latlng: [37.5631, 126.9829], popupContent: '경복궁'}
	];
  
	markers.forEach(function(markerInfo) {
		var marker = L.marker(markerInfo.latlng).addTo(map);
		marker.bindPopup(markerInfo.popupContent);
	});

  	// 클릭한 위치의 정보 업데이트 함수
	function updateInfoPanel(latlng) {
		// 클릭한 위치의 위도와 경도 업데이트
		document.getElementById('latitude').textContent = latlng.lat.toFixed(6);
		document.getElementById('longitude').textContent = latlng.lng.toFixed(6);

		// 클릭한 위치의 주소를 지오코딩을 통해 가져오기
		fetch('https://api.opencagedata.com/geocode/v1/json?q=' + latlng.lat + '+' + latlng.lng + '&key=YOUR_OPENCAGE_API_KEY')
			.then(response => response.json())
			.then(data => {
				const address = data.results[0].formatted;
				document.getElementById('address').textContent = address;
			})
			.catch(error => {
				console.error('Error fetching address:', error);
			});
	}

	// 지도 클릭 이벤트 리스너
	map.on('click', function (e) {
		// 클릭한 지점의 정보를 사용하여 정보 패널 내용을 업데이트
		updateInfoPanel(e.latlng);
	});