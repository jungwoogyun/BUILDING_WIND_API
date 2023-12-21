var nConytol;
/*
* 날씨 변수
*/
const sunny = "1"; // 맑음
const many_cloudy = "3"; // 구름많음
const cloudy = "4"; // 흐림

const none = "0"; // 없음
const rain = "1"; // 비
const snow_rain = "2"; // 비/눈
const snow = "3"; // 눈
const shower = "4"; // 소나기
const raindrop = "5"; // 빗방울
const raindrop_blowingsnow = "6"; // 빗방울/눈날림
const blowingsnow = "7"; // 눈날림
/*
*  날짜 변수
*/
var now = new Date();

var year = now.getFullYear();
var month = ('0' + (now.getMonth() + 1)).slice(-2);
var day = ('0' + now.getDate()).slice(-2);
var dateString = year+ month + day;
/*
* 시간 변수
*/
var hours = ('0' + now.getHours()).slice(-2);
var minutes = ('0' + now.getMinutes()).slice(-2);
var seconds = ('0' +now.getSeconds()).slice(-2);
/*
* 30분 무조건 붙이기
*/
function time(){
// console.log(minutes);
	if (minutes > "30"){
		return hours + "30";
	}else {
		var getHoursTime = now.getHours() - 1;
		var setHoursTime = ('0' + getHoursTime).slice(-2);
		return  setHoursTime +"30";
	}
}
/*
* 정시로만 만들기
*/
function fixed_time(){
	if (minutes > "30"){
		return now.getHours() + 1;
	}else {
		return hours;
	}
}
/*
* 동네 예보
*/
function api_forecast() {
  mymap.on('mousedown', function(e) {
    // 여기서 위도경도를 좌표로 바꿔주도록 한다
  	var grid = dfs_xy_conv("toXY",e.latlng.lat,e.latlng.lng);
    var url = new URL("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst");
    var params = {
      serviceKey: '${key_weather}',
      pageNo: 1,
      numOfRows: 60, // 10개의 카테고리에서 6개씩 나옴
      dataType: 'json',
      base_date: dateString,
      base_time: time(),
                      // 변환된 정보를 넣어준다
      nx: grid.x,
      ny: grid.y
    };
    url.search = new URLSearchParams(params).toString();

    fetch(decodeURI(url)) // url 입력, GET메서드임
      .then(res => {
      // console.log(res);
      // response 처리
      // 응답을 JSON 형태로 파싱
      return res.json();
    })
      .then(data => {
      weather_data(data);
      //console.log(data);
    })
      .catch(err => {
      // error 처리
      console.log('Fetch Error', err);
    });
  })
}