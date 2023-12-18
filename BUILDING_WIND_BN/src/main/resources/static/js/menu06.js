let dataArr = [
  {category : "N",value : 0},
  {category : "NNE",value : 0},
  {category : "NE",value : 0},
  {category : "ENE",value : 0},
  {category : "E",value : 0},
  {category : "ESE",value : 0},
  {category : "SE",value : 0},
  {category : "SSE",value : 0},
  {category : "S",value : 0},
  {category : "SSW",value : 0},
  {category : "SW",value :0},
  {category : "WSW",value : 0},
  {category : "W",value : 0},
  {category : "WNW",value : 0},
  {category : "NW",value : 0},
  {category : "NNW",value : 0},
  {category : "N",value : 0}
];


var chart; // 차트 객체를 전역 변수로 선언


// RIGHTCHART FUNCTION
am5.ready(function() {

        // Create root element
        // https://www.amcharts.com/docs/v5/getting-started/#Root_element
        var root = am5.Root.new("chartdivRight");

        // Set themes
        // https://www.amcharts.com/docs/v5/concepts/themes/
        root.setThemes([
          am5themes_Animated.new(root)
        ]);

        // Generate and set data
        // https://www.amcharts.com/docs/v5/charts/radar-chart/#Setting_data


        // Create chart
        // https://www.amcharts.com/docs/v5/charts/radar-chart/
        chart = root.container.children.push(am5radar.RadarChart.new(root, {
          panX: false,
          panY: false,
          wheelX: "panX",
          wheelY: "zoomX",

        }));

        // Add cursor
        // https://www.amcharts.com/docs/v5/charts/radar-chart/#Cursor
        var cursor = chart.set("cursor", am5radar.RadarCursor.new(root, {
          behavior: "zoomX"
        }));

        cursor.lineY.set("visible", false);

        // Create axes and their renderers
        // https://www.amcharts.com/docs/v5/charts/radar-chart/#Adding_axes
        var xRenderer = am5radar.AxisRendererCircular.new(root, {});
        xRenderer.labels.template.setAll({
          radius: 30,

        });

        var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
          maxDeviation: 0,
          categoryField: "category",
          renderer: xRenderer,
          tooltip: am5.Tooltip.new(root, {}),

        }));



        var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
          renderer: am5radar.AxisRendererRadial.new(root, {}),
          min : 0,
          baseValue : 0
        }));

        // Create series
        // https://www.amcharts.com/docs/v5/charts/radar-chart/#Adding_series
        for (var i = 0; i < 1; i++) {
          var series = chart.series.push(am5radar.RadarColumnSeries.new(root, {
            stacked: true,
            name: "풍속",
            xAxis: xAxis,
            yAxis: yAxis,
            valueYField: "value",
            categoryXField: "category"
          }));

          series.columns.template.setAll({
            tooltipText: "{name}: {valueY}"
          });

          series.data.setAll(dataArr);

          series.appear(1000);
        }

        // Add scrollbars



        xAxis.data.setAll(dataArr);

        // Animate chart
        // https://www.amcharts.com/docs/v5/concepts/animations/#Initial_animation
        chart.appear(1000, 100);


 })




// LEFT CHART------------------------------------------------------


var leftConfig = {
      type: 'line',
      data: {
              labels: [''],
              fill: false,
              datasets: [{
                  label: '풍속',
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
                  text: (ctx) => 'Point Style: ' + ctx.chart.data.datasets[0].pointStyle,
                  }
               },
              elements : {
                  line : {
                      tension : 0
                  }
              }

              }
}
const ctx = document.getElementById('leftChart').getContext('2d');
var leftChart = new Chart(ctx,leftConfig);



//const LeftChart = ()=>{
//
//
//}
//LeftChart();
