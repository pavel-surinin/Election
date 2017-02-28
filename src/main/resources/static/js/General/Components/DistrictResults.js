function getGeneralInfo(self) {
  var dis = self.props.district;
  var districtInfo = [];
  if (dis.name) { districtInfo.push(<tr><td>{dis.name} apylinkė</td></tr>);}
  if (dis.adress) { districtInfo.push(<tr><td>Adresas: {dis.adress}</td></tr>);}
  if (dis.representativeName) { districtInfo.push(<tr><td>Apylinkės atstovas: {dis.representativeName}</td></tr>);}
  if (dis.countyName) { districtInfo.push(<tr><td>Apygarda: {dis.countyName}</td></tr>);}
  if (dis.numberOfElectors) { districtInfo.push(<tr><td>Rinkėjų skaičius: {dis.numberOfElectors}</td></tr>);}
  if (dis.spoiledMulti) { districtInfo.push(<tr><td>Sugadintų biuletenių daugiamandatėje: {dis.spoiledMulti}</td></tr>);}
  if (dis.spoiledSingle) { districtInfo.push(<tr><td>Sugadintų biuletenių vienmandatėje: {dis.spoiledSingle}</td></tr>);}
  if (dis.votesSingleRegisteredDate) { districtInfo.push(<tr><td>Vienmandatės balsų registravimo data/laikas: {dis.votesSingleRegisteredDate}</td></tr>);}
  if (dis.votesMultiRegisteredDate) { districtInfo.push(<tr><td>Daugiamandatės balsų registravimo data/laikas: {dis.votesMultiRegisteredDate}</td></tr>);}
  return districtInfo;
}
function getSingleTable(self){
  var singleTable = [];
  if (self.props.results.votesByCandidate) {
    singleTable = self.props.results.votesByCandidate.map(function(cid,index){
      return (
        <tr key={index} className='small'>
          <td>{cid.candidate.name + ' ' + cid.candidate.surname}</td>
          <td>{cid.candidate.partijosPavadinimas}</td>
          <td>{cid.votes}</td>
          <td>{Math.round(cid.votes/self.props.results.valid*100,2)}%</td>
          <td>{Math.round(cid.votes/self.props.results.voters*100,2)}%</td>
        </tr>
      );
    });
  }
  return singleTable;
}

function getMultiTable(self) {
  if (self.props.results.votesByParty) {
    var multiTable = self.props.results.votesByParty.map(function(p,index){
      var rating = <DistrictResultsMultiTableRow info={p} />;
      if (self.props.results.votesByParty) {
        return(
          <tr key={index}>
          <td>{p.partyNumber}</td>
          <td>{p.name}</td>
          <td>{rating}</td>
          <td>{p.votes}</td>
          <td>{Math.round(p.votes/self.props.results.valid*100,2)}%</td>
          <td>{Math.round(p.votes/self.props.results.voters*100,2)}%</td>
          </tr>);
      }});
    return multiTable;
  }
}
function prepareCanvas(chartType,electionType){
  var sp1 = document.createElement('canvas');
  sp1.id = 'canvas' + chartType + electionType;
  var parentDiv = document.getElementById('parent' + chartType + electionType);
  parentDiv.removeChild(document.getElementById('canvas' + chartType + electionType));
  parentDiv.appendChild(sp1);
}

var DistrictResults  = React.createClass({
  loadSingle : function() {
    prepareCanvas('Pie','Single');
    prepareCanvas('Bar','Single');
    chartss.pie(document.getElementById('canvasPieSingle'), this.props.results, 'Single');
    chartss.bar('horizontalBar',document.getElementById('canvasBarSingle'), this.props.results,'Single');
  },
  loadMulti : function() {
    prepareCanvas('Pie','Multi');
    prepareCanvas('Bar','Multi');
    chartss.pie(document.getElementById('canvasPieMulti'), this.props.results, 'Multi');
    chartss.bar('bar',document.getElementById('canvasBarMulti'), this.props.results, 'Multi');
  },
  componentWillUpdate: function(nextProps, nextState) {
    if (nextProps.district.id != this.props.district.id) {
      console.log('s');
    }
  },
  render: function() {
    var districtInfo = getGeneralInfo(this);
    var singleTable = getSingleTable(this);
    var multiTable = getMultiTable(this);
    var singleStyle = styles.toggleResultNav(this.props.results.votesByCandidate);
    var multiStyle = styles.toggleResultNav(this.props.results.votesByParty);
    console.log("this from districtResults",this);
    return (
      <div>
        <div className="container">
        <h1 className='yellow'>{this.props.district.name} rinkimų apylinkė</h1></div>
        {/* nav-pills  */}
        <ul  className="nav nav-pills secondmenu">
        <li className="active">
        <a  href="#1a" data-toggle="tab">Apylinkės Informacija</a>
        </li>
        <li style={singleStyle}>
        <a onClick={this.loadSingle} href="#2a" data-toggle="tab">Vienmandatės rezultatai</a>
        </li>
        <li style={multiStyle}>
        <a onClick={this.loadMulti} href="#3a" data-toggle="tab">Daugiamandatės rezultaitai</a>
        </li>
        </ul>
          <div id="exTab1" className="container shadow">
            <div className="tab-content clearfix">
              {/* General info tab-1 */}
              <div className="tab-pane active" id="1a">
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <th className='col-md-10 col-sm-10'>Bendra Informacija</th>
                    </tr>
                  </thead>
                  <tbody>
                    {districtInfo}
                  </tbody>
                </table>
              </div>
              {/* Single results tab-2 */}
              <div className="tab-pane vytis" id="2a">
                <h3>Rinkėjų aktyvumas: {this.props.results.valid} balsavusių iš {this.props.results.voters}, {Math.round(this.props.results.valid / this.props.results.voters *100,2)}%</h3>
                <h3>Sugadintų biuletenių skaičius: {this.props.results.spoiledSingle}</h3>
                <div className='col-md-7'>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <th className='col-md-4 col-sm-4'>Kandidatas</th>
                      <th className='col-md-3 col-sm-3'>Partija</th>
                      <th className='col-md-2 col-sm-2'>Balsai</th>
                      <th className='col-md-2 col-sm-2'>% nuo galiojančių</th>
                      <th className='col-md-1 col-sm-2'>% nuo visų</th>
                    </tr>
                  </thead>
                  <tbody>
                    {singleTable}
                  </tbody>
                </table>
                {/*chart pie*/}
                </div>
                <div className='chartContainer col-md-5' id='parentPieSingle'>
                  <canvas id='canvasPieSingle'></canvas>
                </div>
                {/*chart bar*/}
                <div className='chartContainer col-md-12' id='parentBarSingle'>
                  <canvas id='canvasBarSingle'></canvas>
                </div>
              </div>
              {/* Multi results tab-3 */}
              <div className="tab-pane vytis" id="3a">
                <h3>Rinkėjų aktyvumas: {this.props.results.valid} balsavusių iš {this.props.results.voters}, {Math.round(this.props.results.valid / this.props.results.voters *100,2)}%</h3>
                <h3>Sugadintų biuletenių skaičius: {this.props.results.spoiledMulti}</h3>
                <div className='col-md-7'>
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <th className='col-md-1 col-sm-1'>Nr.</th>
                      <th className='col-md-5 col-sm-3'>Partija</th>
                      <th className='col-md-2 col-sm-2'>Retingai</th>
                      <th className='col-md-2 col-sm-2'>Balsai</th>
                      <th className='col-md-2 col-sm-2'>% nuo galiojančių</th>
                      <th className='col-md-2 col-sm-2'>% nuo visų</th>
                    </tr>
                  </thead>
                  <tbody>
                    {multiTable}
                  </tbody>
                </table>
                {/*chart pie*/}
                </div>
                <div className='chartContainer col-md-5' id='parentPieMulti'>
                  <canvas id='canvasPieMulti'></canvas>
                </div>
                {/*chart bar*/}
                <div className='chartContainer col-md-12' id='parentBarMulti'>
                  <canvas id='canvasBarMulti'></canvas>
                </div>
              </div>
          </div>
        </div>
      </div>

    );
  },

});

window.DistrictResults = DistrictResults;
