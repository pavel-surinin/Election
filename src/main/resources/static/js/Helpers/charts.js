var solid = ['rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)','rgba(0, 172, 230, 0.79)']
var doghnutColors = ['rgba(0, 153, 0, 0.85)','rgba(255, 26, 26, 0.85)','rgba(255, 153, 51, 0.85)'];
var backgroundColorArr = [
  'rgba(255, 51, 51, 0.2)',
  'rgba(51, 255, 51, 0.2)',
  'rgba(255, 99, 132, 0.2)',
  'rgba(54, 162, 235, 0.2)',
  'rgba(255, 206, 86, 0.2)',
  'rgba(75, 192, 192, 0.2)',
  'rgba(153, 102, 255, 0.2)',
  'rgba(255, 159, 64, 0.2)',
  'rgba(255, 51, 51, 0.2)',
  'rgba(255, 153, 51, 0.2)',
  'rgba(255, 255, 51, 0.2)',
  'rgba(153, 255, 51, 0.2)',
  'rgba(51, 255, 153, 0.2)',
  'rgba(51, 255, 255, 0.2)',
  'rgba(51, 153, 255, 0.2)',
  'rgba(51, 51, 255, 0.2)',
  'rgba(153, 51, 255, 0.2)',
  'rgba(255, 51, 255, 0.2)',
  'rgba(255, 51, 153, 0.2)',
  'rgba(255, 51, 153, 0.2)',
  'rgba(255, 51, 153, 0.2)',
];
var borderColorArr = [
  'rgba(255, 99, 132, 0.85)',
  'rgba(54, 162, 235, 0.85)',
  'rgba(255, 206, 86, 0.85)',
  'rgba(75, 192, 192, 0.85)',
  'rgba(153, 102, 255, 0.85)',
  'rgba(255, 159, 64, 0.85)',
  'rgba(255, 51, 51, 0.85)',
  'rgba(255, 153, 51, 0.85)',
  'rgba(255, 255, 51, 0.85)',
  'rgba(153, 255, 51, 0.85)',
  'rgba(51, 255, 153, 0.85)',
  'rgba(51, 255, 255, 0.85)',
  'rgba(51, 153, 255, 0.85)',
  'rgba(51, 51, 255, 0.85)',
  'rgba(153, 51, 255, 0.85)',
  'rgba(255, 51, 255, 0.85)',
  'rgba(255, 51, 153, 0.85)',
  'rgba(255, 51, 51, 0.85)',
  'rgba(51, 255, 51, 0.85)',
];
var chartDataMapper = {
  getDataMulti :
  function(results){
    if (results.votesByParty) {
      var labels = results.votesByParty.map(function(pid){
        return pid.name;
      });
      var votes = results.votesByParty.map(function(pid){
        return pid.votes;
      });
      return {labels : labels, votes : votes};
    } else {
      return null;
    }
  },
  getDataCountyMulti :
  function(results){
    if (results.votesByParty) {
      var labels = results.votesByParty.map(function(pid){
        return pid.par.name;
      });
      var votes = results.votesByParty.map(function(pid){
        return pid.votes;
      });
      return {labels : labels, votes : votes};
    } else {
      return null;
    }
  },
  getDataSingle :
  function(results){
    if (results.votesByCandidate) {
      var labels = results.votesByCandidate.map(function(cid){
        return cid.candidate.name + ' ' + cid.candidate.surname;
      });
      var votes = results.votesByCandidate.map(function(cid){
        return cid.votes;
      });
      return {labels : labels, votes : votes};
    } else {
      return null;
    }
  },
  getDataPartyLive :
  function(results){
    if (results) {
      var labels = results.mandatesPerPartyGeneralLive.map(function(line){
        return line.name;
      });
      var votes = results.mandatesPerPartyGeneralLive.map(function(line){
        return line.number;
      });
      return {labels : labels, votes : votes};
    } else {
      return {labels : [], votes : []};
    }
  },
  getDataPartyVotes:
    function(results){
      if(results){
        var labels = results.votesInMulti.map(function(vid){
          return vid.par.name;
        });
        var votes = results.votesInMulti.map(function(vid){
          return vid.votes;
        });
        return{labels: labels, votes: votes};
      } else {
        return {labels: [], votes: []};
      }
    },
};

var chartss = {
  bar : function(alignment,ctx,data,electionType){
    //bar or horizontalBar
    //var mdata = electionType == 'Single' ? chartDataMapper.getDataSingle(data) : chartDataMapper.getDataMulti(data);
    var mdata =[];
    if(electionType == 'Single'){mdata = chartDataMapper.getDataSingle(data);}
    if(electionType =='Multi'){mdata = chartDataMapper.getDataMulti(data);}
    if (electionType =='MultiCounty'){mdata = chartDataMapper.getDataCountyMulti(data);}
    if (electionType =='PartyLive'){mdata = chartDataMapper.getDataPartyLive(data);}
    if(electionType =='PartyVotes'){mdata = chartDataMapper.getDataPartyVotes(data);}
    var myChart = new Chart(ctx, {
      type: alignment,
      data: {
        labels : mdata.labels,
        datasets : [{
          label : 'balsų skaičius',
          data : mdata.votes,
          backgroundColor : solid.slice(1, mdata.labels.length+1),
          borderColor : solid.slice(1, mdata.labels.length+1),
          borderWidth : 1,
        }]
      },
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero:true
            }
          }]
        }
      }
    });
    return myChart;
  },
  pie : function(ctx,data,electionType){
    var mdata =[];
    if(electionType == 'Single'){mdata = chartDataMapper.getDataSingle(data);}
    if(electionType =='Multi'){mdata = chartDataMapper.getDataMulti(data);}
    if (electionType =='MultiCounty'){mdata = chartDataMapper.getDataCountyMulti(data);}
    var myChart = new Chart(ctx,{
      type: 'pie',
      data: {
        labels: mdata.labels,
        datasets: [
          {
            data: mdata.votes,
            backgroundColor: borderColorArr.slice(1, mdata.labels.length+1),
            hoverBackgroundColor: borderColorArr.slice(1, mdata.labels.length+1),
          }]
      },
      //Chart.defaults.global.legend
      options: {
        legend: {
          position : 'bottom',
          display: true,
          labels: {
            fontColor: '#333333'
          }
        }
      }
    });
    return myChart;
  },
  doghnut : function(ctx,data,electionType) {
    var mdata =[];

    if (electionType =='DistrictsProgress'){mdata = data;}
    var myDoughnutChart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: mdata.labels,
        datasets: [
          {
            data: mdata.votes,
            backgroundColor: doghnutColors.slice(0, mdata.labels.length+1),
            hoverBackgroundColor: doghnutColors.slice(0, mdata.labels.length+1),
          }]
      },
      //Chart.defaults.global.legend
      options: {
        legend: {
          position : 'bottom',
          display: true,
          labels: {
            fontColor: '#333333'
          }
        }
      }
    });
    return myDoughnutChart;
  },
};

window.chartss = chartss;
