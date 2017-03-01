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
              <h4 className="panel-title">
                <a onClick={this.loadBarPartyLive} role="button" data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                  Partijos Gauna Mandatų (Gyvai)
                </a>
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
        {/*PARTY LIFE CHART END*/}

        {/*VOTING PROGRESS DONUT START*/}
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingThree">
              <h4 className="panel-title">
                <a onClick={this.loadDoghnutDistrict} className="collapsed" role="button" data-toggle="collapse" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                  progress barai spurgos
                </a>
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
              <h4 className="panel-title">
                <a className="collapsed" role="button" data-toggle="collapse" href="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
                  Daugiamandates isrinktuju sarasas
                </a>
              </h4>
            </div>
            <div id="collapseFour" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                Kas praejo daugiamandateje.
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingFive">
              <h4 className="panel-title">
                <a className="collapsed" role="button" data-toggle="collapse" href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                  Galutinis sarasas
                </a>
              </h4>
            </div>
            <div id="collapseFive" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                Sarasas su visais seimunais.
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

});

window.GeneralresultsComponent = GeneralresultsComponent;
