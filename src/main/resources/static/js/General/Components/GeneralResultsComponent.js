function getSingleWinners(self){
  var singleWinners = [];
  if (self.props.results.singleWinners){
    singleWinners = self.props.results.singleWinners.map(function(sid,index){
      return(
        <tr key={index}>
          <td>{sid.candidate.name + ' ' + sid.candidate.surname}</td>
          <td>{sid.candidate.partijosPavadinimas}</td>
          <td>{sid.candidate.countyName}</td>
          <td>{sid.votes}</td>
        </tr>
      );
    });
  }
    return singleWinners;
}
function getMultiWinners(self){
  var multiWinners = [];
  if(self.props.results.multiWinners){
    multiWinners = self.props.results.multiWinners.map(function(mid,index){
      return(
        <tr key={index}>
          <td>{mid.name + ' ' + mid.surname}</td>
          <td>{mid.partijosPavadinimas}</td>
          <td>{mid.numberInParty}</td>
        </tr>
      );
    });
    return multiWinners;
  }

}
function showSingleWinners(self){
  var singleWinners = getSingleWinners(self);
  if (self.props.results) {
    if (self.props.results.districtsVoted == self.props.results.districtsCount) {
      return (
        <div className="panel panel-default" style={{borderRadius: '1px'}}>
          <div className="panel-heading" role="tab" id="headingSix">
            <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                href="#collapseSix" aria-expanded="false" aria-controls="collapseSix" ref='title' id='heading'>
              Vienmandatės nugalėtojų sąrašas
              <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
            </h4>
          </div>
          <div id="collapseSix" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
            <div className="panel-body">
              <table width="100%" className="table table-striped" id="responsive-table1">
                <thead>
                <tr>
                  <th className='col-md-4 col-sm-4'>Kandidatas</th>
                  <th className='col-md-3 col-sm-3'>Partija</th>
                  <th className='col-md-3 col-sm-3'>Apygarda</th>
                  <th className='col-md-2 col-sm-2'>Balsai</th>
                </tr>
                </thead>
                <tbody>
                {singleWinners}
                </tbody>
              </table>
              <div className="pull-right">
                <i className="fa fa-compress" role="button" data-toggle="collapse"
                   href="#collapseSix" aria-expanded="false" aria-controls="collapseSix"
                   style={{transform: 'rotate(-45deg)'}}></i>
              </div>
            </div>
          </div>

        </div>
      );
    } else {
      return null;
    }
  } else {
    return null;
  }
}
function showMultiWinners(self){
  var multiWinners = getMultiWinners(self);
  if(self.props.results){
    if(self.props.results.districtsVoted == self.props.results.districtsCount){
      return(
        <div className="panel panel-default" style={{borderRadius: '1px'}}>
          <div className="panel-heading" role="tab" id="headingFour">
            <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                href="#collapseFour" aria-expanded="false" aria-controls="collapseFour" ref='title' id='heading'>
              Daugiamandatės nugalėtojų sąrašas
              <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
            </h4>
          </div>
          <div id="collapseFour" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
            <div className="panel-body">
              <table width="100%" className="table table-striped" id="responsive-table2">
                <thead>
                <tr>
                  <th className='col-md-4 col-sm-4'>Kandidatas</th>
                  <th className='col-md-4 col-sm-4'>Partija</th>
                  <th className='col-md-4 col-sm-4'>Numeris partijoje</th>
                </tr>
                </thead>
                <tbody>
                {multiWinners}
                </tbody>
              </table>
              <div className="pull-right">
                <i className="fa fa-compress" role="button" data-toggle="collapse"
                   href="#collapseFour" aria-expanded="false" aria-controls="collapseFour"
                   style={{transform: 'rotate(-45deg)'}}></i>
              </div>
            </div>
          </div>

        </div>
      );
    } else {
      return null;
    }
  } else {
    return null;
  }
}
function prepareCanvas(chartType,electionType){
  var sp1 = document.createElement('canvas');
  sp1.id = 'canvas' + chartType + electionType;
  var parentDiv = document.getElementById('parent' + chartType + electionType);
  parentDiv.removeChild(document.getElementById('canvas' + chartType + electionType));
  parentDiv.appendChild(sp1);
}
var GeneralresultsComponent = React.createClass({
  loadBarPartyLive : function() {
    prepareCanvas('Bar','PartyLive');
    chartss.bar('horizontalBar', document.getElementById('canvasBarPartyLive'), this.props.results, 'PartyLive');
  },
  loadDoghnutDistrict : function() {
    var res = this.props.results;
    prepareCanvas('Doghnut', 'District');
    var data = {
      labels : ['Prabalsavusios apylinkės', 'Nebalsavusios apylinkės'],
      votes : [res.districtsVoted, (res.districtsCount - res.districtsVoted)]
    };
    chartss.doghnut(document.getElementById('canvasDoghnutDistrict'), data, 'DistrictsProgress');
    prepareCanvas('Doghnut', 'Voters');
    var votersData = {
      labels : ['Prabalsavo rinkėjų','Neatvyko balsuoti rinkėjų'],
      votes : [res.votesCount,(res.votersCount - res.votesCount)],
    };
    chartss.doghnut(document.getElementById('canvasDoghnutVoters'), votersData, 'DistrictsProgress');
  },
  componentDidMount: function() {
    this.loadDoghnutDistrict();
    $(document).ready(function() {
      $('#responsive-table1').DataTable({
        language: {
          url: 'lithuanian.json'
        },
        paging: false,
        responsive: true,
        searching: false,
      });
      $('#responsive-table2').DataTable({
        language: {
          url: 'lithuanian.json'
        },
        paging: false,
        responsive: true,
        searching: false,
      });
    });
  },
  componentWillReceiveProps: function(nextProps) {
    this.loadDoghnutDistrict();
  },
  shouldComponentUpdate: function(nextProps, nextState) {
    console.log(this);
    console.log(nextProps);
    console.log(nextState);
  },
  render: function() {
    var MultiWinnersTable = showMultiWinners(this);
    var SingleWinnersTable = showSingleWinners(this);
    var res = this.props.results;
    return (
      <div className='container'>
        <h1 className='yellow'>Rezultatai</h1>
        <div className="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingOne">

              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" onClick={this.loadBarPartyLive}>
                  Partijų mandatų grafikas (gyvai)
                <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>

            <div id="collapseOne" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">

              <div className="panel-body">
                {/*chart bar*/}
                <h3>Partijos, gauvusios mandatų</h3>
                <div className='chartContainer col-md-12' id='parentBarPartyLive'>
                  <canvas id='canvasBarPartyLive'></canvas>
                </div>
              </div>
            </div>
          </div>

          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingThree">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                  Rinkimų eiga
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>
            <div id="collapseThree" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                <div className='chartContainer col-md-4' id='parentDoghnutDistrict'>
                <h3>Apylinkių balsavimo progresas</h3>
                  <canvas id='canvasDoghnutDistrict'></canvas>
                </div>
                <div className='col-md-4'>
                  <h3>Balsavimo progresas</h3>
                  <table className="table table-striped">
                    <thead>
                    <tr>
                      <th className='col-md-4 col-sm-6'>Įvykis</th>
                      <th className='col-md-3 col-sm-6'>Skaičius</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                      <td>
                      Apylinkių prabalsavo
                      </td>
                      <td>
                      {res.districtsVoted}
                      </td>
                    </tr>
                    <tr>
                      <td>
                      Apylinkių nebalsavo
                      </td>
                      <td>
                      {(res.districtsCount - res.districtsVoted)}
                      </td>
                    </tr>
                    <tr>
                      <td>
                      Viso apylinkių
                      </td>
                      <td>
                      {res.districtsCount}
                      </td>
                    </tr>
                    <tr>
                      <td>
                      Balsavimo progresas
                      </td>
                      <td>
                      {Math.round((res.districtsVoted / res.districtsCount)* 100)}%
                      </td>
                    </tr>
                    </tbody>
                  </table>

                  <h3>Rinkėjų aktyvumas</h3>
                  <table className="table table-striped">
                    <thead>
                    <tr>
                      <th className='col-md-4 col-sm-6'>Įvykis</th>
                      <th className='col-md-3 col-sm-6'>Skaičius</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                      <td>
                      Rinkėjų prabalsavo
                      </td>
                      <td>
                      {res.votesCount}
                      </td>
                    </tr>
                    <tr>
                      <td>
                      Rinkėjų nebalsavo
                      </td>
                      <td>
                      {(res.votersCount - res.votesCount)}
                      </td>
                    </tr>
                    <tr>
                      <td>
                      Viso rinkėjų
                      </td>
                      <td>
                      {res.votersCount}
                      </td>
                    </tr>
                    <tr>
                      <td>
                      Rinkėjų aktyvumas
                      </td>
                      <td>
                      {Math.round((res.votesCount/res.votersCount)*100)}%
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <div className='chartContainer col-md-4' id='parentDoghnutVoters'>
                  <h3>Rinkėjų balsavimo aktyvumas</h3>
                  <canvas id='canvasDoghnutVoters'></canvas>
                </div>
              </div>
            </div>
          </div>
          {MultiWinnersTable}
          {SingleWinnersTable}
        </div>
      </div>
    );
  }
});
window.GeneralresultsComponent = GeneralresultsComponent;
