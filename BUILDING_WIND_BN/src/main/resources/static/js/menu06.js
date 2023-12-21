//------------------------------------------------------
// LEFT CHART
//------------------------------------------------------

var leftConfig = {
  type: 'line',
  data: {
          labels: [''],
          fill: false,
          datasets: [{
              label: '풍속 ',
              data: [''],
              fill: false,
              pointStyle:'circle',
              borderColor: '#2A76C7',
              pointRadius:10,


          }]
      },

      options: {
          responsive: true,
          plugins: {
          title: {
              display: true,
              text: ' 실시간 풍속변화(m/s)',
              align: 'start',
              color: '#2A76C7',
              },
              legend: {
               display: false, // 레전드 감추기
              }


           },
              elements : {
                  line : {
                      tension : 0
                  }
              },

          }
}
const ctx = document.getElementById('leftChart').getContext('2d');

var leftChart = new Chart(ctx,leftConfig);



//------------------------------------------------------
// RIGHT CHART
//------------------------------------------------------
// Parse the data from an inline table using the Highcharts Data plugin

const rightChart =()=>{
  Highcharts.chart('container', {
      data: {
          table: 'freq',
          startRow: 1,
          endRow: 17,
          endColumn: 7
      },

      chart: {
          polar: true,
          type: 'column'
      },

       title: {
           text: null,
       },

      // subtitle: {
      //     text: 'title',
      //     align: 'left'
      // },

      pane: {
          size: '85%'
      },

      // legend: {
      //     align: 'right',
      //     verticalAlign: 'top',
      //     y: 100,
      //     layout: 'vertical'
      // },

      xAxis: {
          tickmarkPlacement: 'on'
      },

      yAxis: {
          min: 0,
          max: 5,
          endOnTick: false,
          showLastLabel: true,
          title: {
              text: '풍속 (m/s)'
          },
          labels: {
              format: '{value} m/s'
          },
          reversedStacks: false
      },

      tooltip: {
          valueSuffix: 'm/s'
      },

      plotOptions: {
          series: {
              stacking: 'normal',
              shadow: false,
              groupPadding:1,
              pointPlacement: 'on',
              showInLegend: false,
          },
          column: {
                  tooltip: {
                      headerFormat: '',
                      pointFormat: '{point.y}'
                  }
           }
      },

      exporting: {
        enabled: false
      }
  });
}

rightChart();