var DistrictResults  = React.createClass({
  loadSingle : function() {
    var sp1 = document.createElement('canvas');
    sp1.id = 'canvasPieSingle';
    var parentDiv = document.getElementById('parentPieSingle');
    parentDiv.removeChild(document.getElementById('canvasPieSingle'));
    parentDiv.appendChild(sp1);
    chartss.pie(document.getElementById('canvasPieSingle'), this.props.results);
    var sp2 = document.createElement('canvas');
    sp2.id = 'canvasBarSingle';
    var parentBar = document.getElementById('parentBarSingle');
    parentBar.removeChild(document.getElementById('canvasBarSingle'));
    parentBar.appendChild(sp2);
    chartss.bar('horizontalBar',document.getElementById('canvasBarSingle'), this.props.results);
  },
  componentWillUpdate: function(nextProps, nextState) {
    if (nextProps.district.id != this.props.district.id) {
      this.loadSingle();
    }
  },

  render: function() {
    console.log(this.props.district);
    var self = this;
    var singleTable = [];
    if (this.props.results.votesByCandidate) {
      singleTable = this.props.results.votesByCandidate.map(function(cid,index){
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
    var dis = this.props.district;
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
    return (
      <div>
      <div className="container"><h1>{this.props.district.name} rinkimų apylinkė</h1></div>
      <div id="exTab1" className="container">
      <ul  className="nav nav-pills">
            <li className="active">
              <a  href="#1a" data-toggle="tab">Apylinkės Informacija</a>
            </li>
            <li><a onClick={this.loadSingle} href="#2a" data-toggle="tab">Vienmandatės rezultatai</a>
            </li>
            <li><a href="#3a" data-toggle="tab">Daugiamandatės rezultaitai</a>
            </li>
          </ul>

            <div className="tab-content clearfix">
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

                </div>
                <div className='chartContainer col-md-5' id='parentPieSingle'>
                  <canvas id='canvasPieSingle'></canvas>
                </div>

                <div className='chartContainer col-md-12' id='parentBarSingle'>
                  <canvas id='canvasBarSingle'></canvas>
                </div>
              </div>

              <div className="tab-pane" id="3a">
                <h3>We applied clearfix to the tab-content to rid of the gap between the tab and the content</h3>
              </div>
            </div>
        </div>
      </div>

    );
  },

});

window.DistrictResults = DistrictResults;
