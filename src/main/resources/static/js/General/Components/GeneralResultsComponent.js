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
function showSingleWinners(self){
  var singleWinners = getSingleWinners(self);
  if(self.props.results){
    if(self.props.results.districtsVoted == self.props.results.districtsCount){
      return(
        <div className="panel panel-default" style={{borderRadius: '1px'}}>
          <div className="panel-heading" role="tab" id="headingSix">
            <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                href="#collapseSix" aria-expanded="false" aria-controls="collapseSix" ref='title' id='heading'>
              Vienmandates nugaletoju sarasas
              <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
            </h4>
          </div>
          <div id="collapseSix" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
            <div className="panel-body">
              <table className="table table-striped">
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
                   href="#collapseSix" aria-expanded="false" aria-controls="collapseSix" style={{transform: 'rotate(-45deg)'}}></i>
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
      labels : ['Prabalsavusios apylinkės', 'Nebelsavusios apylinkės'],
      votes : [res.districtsVoted, (res.districtsCount - res.districtsVoted)]
    };
    chartss.doghnut(document.getElementById('canvasDoghnutDistrict'), data, 'DistrictsProgress');
    prepareCanvas('Doghnut', 'Voters');
    var votersData = {
      labels : ['Rinkėjų skaičius','Prabalsavo rinkėjų'],
      votes : [res.votersCount,(res.votersCount - res.votesCount)],
    };
    chartss.doghnut(document.getElementById('canvasDoghnutVoters'), votersData, 'DistrictsProgress');
  },
  render: function() {
    console.log('GeneralresultsComponent',this);
    return (
      <div className='container'>
        <h1 className='yellow'>Rezultatai</h1>

        <div className="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
        {/*PARTY LIVE CHART START*/}
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingOne">

              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" onClick={this.loadBarPartyLive}>
                  Partijų grafikas (gyvai)
                <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>

            <div id="collapseOne" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">

              <div className="panel-body">
                {/*chart bar*/}
                <div className='chartContainer col-md-12' id='parentBarPartyLive'>
                  <canvas id='canvasBarPartyLive'></canvas>
                </div>
                Superlivegrafikas ouououou
              </div>
            </div>
          </div>


          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingTwo">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                  Kiek praejo bendrai pagal partija grafikas
                <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>

            <div id="collapseTwo" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
              <div className="panel-body">
                Grafikas kuris parodo kiek partija turi nariu seime.
              </div>
            </div>
          </div>

          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingThree">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseThree" aria-expanded="false" aria-controls="collapseThree" onClick={this.loadDoghnutDistrict}>
                  Rinkimu eiga
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>
            <div id="collapseThree" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                <div className='chartContainer col-md-6' id='parentDoghnutDistrict'>
                  <canvas id='canvasDoghnutDistrict'></canvas>
                </div>
                <div className='chartContainer col-md-6' id='parentDoghnutVoters'>
                  <canvas id='canvasDoghnutVoters'></canvas>
                </div>
              </div>
            </div>
          </div>
          {/*VOTING PROGRESS DONUT END*/}

          <div className="panel panel-default" style={{borderRadius: '1px'}}>

            <div className="panel-heading" role="tab" id="headingFour">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                   href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                  Daugiamandates isrinktuju sarasas
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>

              </h4>
            </div>

            <div id="collapseFour" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">

              <div className="panel-body">
                Kas praejo daugiamandateje.
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>

            <div className="panel-heading" role="tab" id="headingFive">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                  Galutinis sarasas
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>

            <div id="collapseFive" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">

              <div className="panel-body">
                Sarasas su visais seimunais.
              </div>
            </div>
          </div>
          {showSingleWinners(this)}
        </div>
      </div>
    );

  }

});

window.GeneralresultsComponent = GeneralresultsComponent;
