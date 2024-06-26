"use client";
// 현재 턴/종목에 대한 차트 정보 (main - 1)
import { useState, useEffect } from "react";
import anychart from "anychart";
import MultiReviewStore from "@/public/src/stores/profile/MultiReviewStore";
// 주어진 데이터 정제
function filteringLowPriceZero(data :any) {
  const newData = data?.map((item :any) => {
    if (item.lowPrice == 0) {
      return {
        ...item,
        lowPrice: item.endPrice,
        marketPrice: item.endPrice,
        highPrice: item.endPrice,
      };
    }
    return item;
  });
  return newData;
}

// 이동평균선 데이터 생성 함수
function calculateMovingAverage(data :any, period :any) {
    const result = [];
    for (let i = 0; i < data?.length; i++) {
        if (i > period) {
          const sum = data.slice(i - period + 1, i + 1).reduce((acc :any, curr:any) => acc + curr.endPrice, 0);
          const average = (sum / period).toFixed(2);
          result.push([data[i].date, parseFloat(average)]);

        } else {
          // result.push([data[i].date, 0]);
        }
    }
    return result;
}


export default function Chart({ data }: any) {
    const { 
        selectedTradeList, 
        startDate,
        endDate,
        stockName,
        
    } = MultiReviewStore();
    useEffect(() => {
        const purifiedData = filteringLowPriceZero(data);

        const minLowPriceList = purifiedData.reduce((acc :any, current :any) => {
            if (!acc.length) {
              acc.push(current);
            } else {
              const minLow = acc[0].lowPrice;
              if (current.lowPrice < minLow) {
                acc.length = 0;
                acc.push(current);
              } else if (current.lowPrice === minLow) {
                acc.push(current);
              }
            }
            return acc;
          }, []);

        const maxHighPriceList = purifiedData.reduce((acc :any, current :any) => {
        if (!acc.length) {
            acc.push(current);
        } else {
            const maxHigh = acc[0].highPrice;
            if (current.highPrice > maxHigh) {
            acc.length = 0;
            acc.push(current);
            } else if (current.highPrice === maxHigh) {
            acc.push(current);
            }
        }
        return acc;
        }, []);

        const maxHighPriceListData :any = []
        maxHighPriceList.map((item :any, index :number) => {
            maxHighPriceListData.push(
                {
                    date : item.date.split('T')[0],
                    price : item.highPrice,
                }
            ) 
        })

        const minLowPriceListData :any = [];
        minLowPriceList.map((item :any, index :number) => {
            minLowPriceListData.push(
                {
                    date : item.date.split('T')[0],
                    price : item.lowPrice,
                }
            ) 
        })
        
        // 차트 생성
        const chart = anychart.stock();
        // 차트를 담을 컨테이너 생성
        const container = chart.container("chart-container")
        const creditsElement = document.querySelector('.anychart-credits');
        if (creditsElement) {
        creditsElement.remove();
        }
        chart.contextMenu(false);
        chart.width("95%");
        // 스크롤러
        const scroller = chart.scroller();
        scroller.selectedFill({
        src: 'https://static.anychart.com/images/beach.png',
        mode: 'stretch',
        opacity: 0.5
        });
        
        // 툴 팁
        const tooltip = chart.tooltip();

        // 첫 번재 plot 생성(line, OHLC, 이동평균선)
        const plot1 = chart.plot(0);
        plot1.title("주가, OHLC, 이동평균선")
        plot1.yAxis().orientation("right");
        plot1.yAxis().labels().fontSize(15);

        // 최저가 Line
        const minPriceLineMarker = plot1.lineMarker(0);
        minPriceLineMarker.value(minLowPriceListData[0]?.price);
        minPriceLineMarker.stroke({
            thickness: 2,
            color: "blue",
            dash: "1 0",
        });    
        // 최저가 Text
        const minPriceTextMarker = plot1.textMarker(0);
        minPriceTextMarker.value(minLowPriceListData[0]?.price);
        minPriceTextMarker.text(minLowPriceListData[0]?.price)
        minPriceTextMarker.fontColor("blue");
        minPriceTextMarker.background().enabled(true);
        minPriceTextMarker.background().stroke("2 blue");
        minPriceTextMarker.padding(3);
        minPriceTextMarker.align("right");
        minPriceTextMarker.offsetX(-60);
        minPriceTextMarker.fontSize(15);

        // 최고가 Line
        const maxPriceLineMarker = plot1.lineMarker(1);
        maxPriceLineMarker.value(maxHighPriceListData[0]?.price);
        maxPriceLineMarker.stroke({
            thickness: 2,
            color: "red",
            dash: "1 0",
        });    
        // 최고가 Text
        const maxPriceTextMarker = plot1.textMarker(1);
        maxPriceTextMarker.value(maxHighPriceListData[0]?.price);
        maxPriceTextMarker.text(maxHighPriceListData[0]?.price)
        maxPriceTextMarker.fontColor("red");
        maxPriceTextMarker.background().enabled(true);
        maxPriceTextMarker.background().stroke("2 red");
        maxPriceTextMarker.padding(3);
        maxPriceTextMarker.align("right");
        maxPriceTextMarker.offsetX(-60);
        maxPriceTextMarker.fontSize(15);

        // line series 생성
        const lineSeries = plot1.line(
        purifiedData?.map((item: any) => [item.date, item.endPrice])
        );
        // line series 속성 설정
        lineSeries.name("주가");
        lineSeries.hovered().markers().enabled(true).type("circle").size(3);
        lineSeries.stroke("#86BF15", 1);
        lineSeries.tooltip().useHtml(true);
        lineSeries.tooltip().format(function (this :any) {
        const series = this.series;
        return (
            "주가 : " + this.value + "\n"
        )
        })
        lineSeries.enabled(false);
        // candlestick series 생성
        const candlestickSeries = plot1.candlestick(purifiedData?.map((item: any) => [item.date, item.marketPrice, item.highPrice, item.lowPrice, item.endPrice]));
        // candlestick series 속성 설정
        candlestickSeries.name("OHLC");
        candlestickSeries.legendItem().iconType("risingfalling");
        candlestickSeries.tooltip().useHtml(true);
        candlestickSeries.tooltip().format(function (this: any) {
        return (
            "시가 : " + this.open + "\n" +
            "고가 : " + this.high + "\n" +
            "저가 : " + this.low + "\n" +
            "종가 : " + this.close + "\n"
        );
        });
        // candlestick series 색상 지정
        candlestickSeries.risingFill("#F65742", 1);
        candlestickSeries.risingStroke("#F65742", 1);
        candlestickSeries.fallingFill("#0597FF", 1);
        candlestickSeries.fallingStroke("#0597FF", 1);

        // 이동평균선 그래프 생성(sma)
        const sma5Series = plot1.line(calculateMovingAverage(purifiedData, 5));
        sma5Series.name('5');
        const sma20Series = plot1.line(calculateMovingAverage(purifiedData, 20));
        sma20Series.name('20');
        const sma60Series = plot1.line(calculateMovingAverage(purifiedData, 60));
        sma60Series.name('60');
        const sma120Series = plot1.line(calculateMovingAverage(purifiedData, 120));
        sma120Series.name('120');
        
        // 이동평균선 그래프 색상 지정
        sma5Series.stroke('purple');
        sma20Series.stroke('red');
        sma60Series.stroke('green');
        sma120Series.stroke('blue');
    
        // 이동평균선 툴팁 내용 지정
        sma5Series.tooltip().useHtml(true);
        sma5Series.tooltip().format(function (this :any) {
        if (this.value) {
            return (
            "sma  05 : " + this.value
            ) 
        } else {
            return (
            "sma  05 : " + 0
            )
        }
        }) 
        sma20Series.tooltip().useHtml(true);
        sma20Series.tooltip().format(function (this :any) {
        if (this.value) {
            return (
            "sma 20 : " + this.value
            ) 
        } else {
            return (
            "sma 20 : " + 0
            )
        }
        }) 
        sma60Series.tooltip().useHtml(true);
        sma60Series.tooltip().format(function (this :any) {
        if (this.value) {
            return (
            "sma 60 : " + this.value
            ) 
        } else {
            return (
            "sma 60 :" + 0
            )
        }
        }) 
        sma120Series.tooltip().useHtml(true);
        sma120Series.tooltip().format(function (this :any) {
        if (this.value) {
            return (
            "sma120 : " + this.value + "\n"
            ) 
        } else {
            return (
            "sma120 : " + 0 + "\n"
            )
        }
        }) 

    
    let eventMarkerData :any = [];
    selectedTradeList?.map((x :any) => {
        if (x.tradeType == "BUY") {
            eventMarkerData.push({
                symbol : x.nickname,
                date : x.date,
                description : `주가 : ${x.price}` + '\n' + `수량 : ${x.amount}`,
                normal : { fill : 'red' }

            })
        } else if (x.tradeType == "SELL") {
            eventMarkerData.push({
                symbol : x.nickname,
                date : x.date,
                description : `주가 : ${x.price}` + '\n' + `수량 : ${x.amount}`,
                normal : { fill : 'blue' }
            })
        } else if (x.tradeType == "SHORT") {
            eventMarkerData.push({
                symbol : x.nickname,
                date : x.date,
                description : `주가 : ${x.price}` + '\n' + `수량 : ${x.amount}`,
                normal : { fill : 'yellow' }

            })
        }
    })

    if (eventMarkerData.length > 0) {
        plot1.eventMarkers({"groups": [
            {
            "data": eventMarkerData,
            }
        ]});
    }
    
    // set the symbol of event markers
    plot1.eventMarkers().format(function( this :any ) {
        return this.getData("symbol");
    });
    
    
    // 첫 번째 plot 속성 설정
    plot1.legend().title().useHtml(true);
    plot1.legend().titleFormat(<span></span>);
    plot1.legend().useHtml(true);
    plot1.legend().itemsFormat(function (this: any) {
    const series = this.series;
    if (series.getType() == "line") {
        if (this.value) {
        return (
            "<span style='color:#455a64;font-weight:600'>" +
            series.name() +
            ":</span>" +
            this.value
        );
        } else {
        return (
            "<span style='color:#455a64;font-weight:600'>" +
            series.name() +
            ":</span>" +
            0
        )
        }
    }
    if (series.getType() == "candlestick") {
        return (
        "<span style='color:#455a64;font-weight:600'>" +
        series.name() +
        ":</span>" +
        this.open +
        " | " +
        this.high +
        " | " +
        this.low +
        " | " +
        this.close
        );
    }
    });

    // 2번째 plot 생성(거래량)
    const plot2 = chart.plot(1);
    plot2.title("거래량")
    plot2.yAxis().orientation("right");
    plot2.yAxis().labels().fontSize(15)

    plot2.legend().title().useHtml(true);
    plot2.legend().titleFormat(<span></span>);
    const columnSeries = plot2.column(
    purifiedData?.map((item: any) => [item.date, item.tradingVolume])
    );
    columnSeries.name("거래량");
    columnSeries.risingFill("#F65742", 1);
    columnSeries.risingStroke("#F65742", 1);
    columnSeries.fallingFill("#0597FF", 1);
    columnSeries.fallingStroke("#0597FF", 1);

    plot2.legend().useHtml(true);
    plot2.legend().itemsFormat(function (this: any) {
    const series = this.series;
    if (series.getType() == "column") {
        return (
        "<span style='color:#455a64;font-weight:600'>" +
        series.name() +
        ":</span>" +
        this.value
        );
    }
    });
    plot1.height("70%");
    plot2.height("30%");
    
    chart.draw();
    return () => {
        chart.dispose();
        };
        
    }, [data]);
 
  
  return (
    <div className="row-span-12 grid grid-rows-12">
        <div className="row-span-1 text-center">
            <div className="bg-blue-200 m-auto rounded-t-md">종목명 : {stockName} ({startDate} ~ {endDate})</div>
        </div>
        <div id="chart-container" className="row-span-12 flex items-center justify-center"></div>
    </div>

  );
}
