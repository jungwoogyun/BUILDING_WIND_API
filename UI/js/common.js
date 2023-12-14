
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

let menuActiveArr = []; //활성화 여부
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
            
            //ALERTPOPUP WINDOW
            const submenuUrl =  item.getAttribute('data-submenu');
            const submenuIdx = item.getAttribute('data-idx');
            
            var popupWidth = 1200;
            var popupHeight = 800;
            var popupX = (window.screen.width / 2) - (popupWidth / 2);
            // 만들 팝업창 width 크기의 1/2 만큼 보정값으로 빼주었음
            var popupY= (window.screen.height / 2) - (popupHeight / 2);
            // 만들 팝업창 height 크기의 1/2 만큼 보정값으로 빼주었음
            menuActiveArr[submenuIdx] =  window.open(submenuUrl+".html", '', 'status=no, height=' + popupHeight  + ', width=' + popupWidth  + ', left='+ popupX + ', top='+ popupY);
            console.log(submenuIdx,menuActiveArr[submenuIdx]);
            
            //팝업창 x버튼에 대한 이벤트 처리

            

        }else{
            //IMAGE CHANGE
            item.setAttribute("data-toggle","off");
            const imgEl = item.firstElementChild;
            let str = imgEl.getAttribute('src');
            str = str.substring(0,str.indexOf('.'))+"_off.png";
            imgEl.setAttribute('src',str);

            //ALERTPOPUP WINDOW CLOSE
            const submenuIdx = item.getAttribute('data-idx');
            menuActiveArr[submenuIdx].close();
            

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
  console.log('자식 창으로부터 받은 메시지:', receivedMessage);
  
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
  const laloArr = []; // 고정된 좌표값 넣기..
  
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