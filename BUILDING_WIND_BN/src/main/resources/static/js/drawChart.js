
let intervalId  = null;     //Interval Null유무

console.log("drawChart_ByRealTimeMenu() Not called.. intervalId : ",intervalId);



   //실시간 풍향
   let realtimeVECIdx =["N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW","N"]
   let realtimeVECVal =["299","281","226","233","219","247","225","240","231","253"];

   //실시간 풍속
   let realtimeWSDIdx = ["0600","0700","0800","0900","1000","1100","1200","1300","1400","1500"];
   let realtimeWSDVal = [0.9,1.4,2.4,1.9,3.3,2.3,3.7,3.8,2.4,4.2];


const drawChart_ByRealTimeMenu = ()=>{


if(true){


    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth() + 1; // 월은 0부터 시작하므로 1을 더합니다.
    var day = currentDate.getDate();

    // 월과 일이 한 자리 수일 경우 앞에 0을 붙입니다.
    month = month < 10 ? '0' + month : '' + month;
    day = day < 10 ? '0' + day : '' + day;

    var formattedDate = year + month + day;

    console.log(formattedDate);


    
 


    idx=0;
    let basetime=null;


           
            //2초마다 올리기
            intervalId = setInterval(function(){

               
                    //----------------
                    //    left Chart
                    //----------------
                    var newDataPoint = {
                        label:realtimeWSDIdx[idx],
                        value:realtimeWSDVal[idx]
                    };

                    

                    leftConfig.data.labels.push(newDataPoint.label);
                    leftChart.data.datasets[0].data.push(newDataPoint.value);
                    
                    leftChart.update();

                    //----------------
                    //    right Chart
                    //----------------
                    //풍향 기호부터 구하기
                    directionIdx =  parseInt( ( parseInt(realtimeVECVal[idx]) + 22.5 * 0.5)/22.5); //풍향문자 구하기
                    console.log("realtimeVECVal[idx] : " + realtimeVECVal[idx]);
                    console.log("directionIdx : " + directionIdx);
                    console.log("realtimeVECIdx[directionIdx] : " +realtimeVECIdx[directionIdx]);


                     const leftTableTrDirEls =    document.querySelectorAll("#freq .dir");
                     leftTableTrDirEls.forEach(dir=>{
                        if(dir.innerHTML == realtimeVECIdx[directionIdx])
                        {
                            const dataEl =  dir.nextElementSibling;
                            dataEl.innerHTML = realtimeWSDVal[idx];
                        }
                        else
                        {
                            const dataEl =  dir.nextElementSibling;
                            dataEl.innerHTML = 0;
                        }

                     });

                    //----------------
                    //RIGHT CHART
                    //----------------
                        const ChartBlockRightEl =  document.querySelector('.chartBlock .right');
                        ChartBlockRightEl.removeChild(ChartBlockRightEl.children[0]);
                        const newrightChartEl = document.createElement('div');
                        newrightChartEl.setAttribute('id','container');
                        ChartBlockRightEl.appendChild(newrightChartEl);
                        rightChart();


                    //----------------
                    //REALTIME 테이블에 추가
                    //----------------
                    const tblEl = document.querySelector('.realtimeTbl tbody');
                    const trEl = document.createElement('tr');
                    const td1 = document.createElement('td'); td1.innerHTML=idx + 1;
                    const td2 = document.createElement('td'); td2.innerHTML=realtimeWSDIdx[idx];
                    const td3 = document.createElement('td'); td3.innerHTML= realtimeVECVal[idx]+"/"+realtimeVECIdx[directionIdx]
                    const td4 = document.createElement('td'); td4.innerHTML= realtimeWSDVal[idx];

                    trEl.appendChild(td1);trEl.appendChild(td2);
                    trEl.appendChild(td3);trEl.appendChild(td4);
                    tblEl.appendChild(trEl);

                    //스크롤바를 아래로 내리기
                    const sectionLeftEl = document.querySelector('.section06>.right>.tbl_block');
                    sectionLeftEl.scrollTop = container.scrollHeight;

                    //----------------
                    // IDX 증가
                    //----------------
                    idx++;
                    if(idx>=realtimeVECVal.length){
                        clearInterval(intervalId);
                        idx=0;
                    }

            },2000);
        
        
         console.log("drawChart_ByRealTimeMenu() called.. intervalId : ",intervalId);
            return intervalId;
        }  


        realtimeVECVal =[];
        //실시간 풍속
        realtimeWSDIdx = [];
        realtimeWSDVal = [];
        intervalId = null;
        
}//drawChart();

