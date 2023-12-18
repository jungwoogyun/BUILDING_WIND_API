

// RIGHTCHART FUNCTION
    const windDirection = ["N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW","N"];

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
        var cat = -1;
        var value = 10;

        function generateData() {
          value = Math.round(Math.random() * 10);
          cat++;
          return {
            category: "cat" + cat,
            value: value
          };
        }

        function generateDatas(count) {

          var data = [];
          for(i=0;i<windDirection.length;i++){
                data.push({category : windDirection[i],value :0});
          }
            return data;
        }

        // Create chart
        // https://www.amcharts.com/docs/v5/charts/radar-chart/
        var chart = root.container.children.push(am5radar.RadarChart.new(root, {
          panX: false,
          panY: false,
          wheelX: "panX",
          wheelY: "zoomX"
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
          radius: 10
        });

        var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
          maxDeviation: 0,
          categoryField: "category",
          renderer: xRenderer,
          tooltip: am5.Tooltip.new(root, {})
        }));

        var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
          renderer: am5radar.AxisRendererRadial.new(root, {})
        }));

        // Create series
        // https://www.amcharts.com/docs/v5/charts/radar-chart/#Adding_series
        for (var i = 0; i < 1; i++) {
          var series = chart.series.push(am5radar.RadarColumnSeries.new(root, {
            stacked: true,
            name: "풍속 : " ,
            xAxis: xAxis,
            yAxis: yAxis,
            valueYField: "value",
            categoryXField: "category"
          }));

          series.columns.template.setAll({
            tooltipText: "{name}: {valueY}"
          });

          series.data.setAll(generateDatas(8));

          series.appear(1000);
        }

        // Add scrollbars
        //chart.set("scrollbarX", am5.Scrollbar.new(root, { orientation: "horizontal" }));
        //chart.set("scrollbarY", am5.Scrollbar.new(root, { orientation: "vertical" }));

        var data = generateDatas(8);
        xAxis.data.setAll(data);

        // Animate chart
        // https://www.amcharts.com/docs/v5/concepts/animations/#Initial_animation
        chart.appear(1000, 100);

        }); // end am5.ready()









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
