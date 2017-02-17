var DistrictResults  = React.createClass({
  loadPieSingle : function() {
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

  render: function() {
    var self = this;
    var singleTable = [];
    if (this.props.results.votesByCandidate) {
      singleTable = this.props.results.votesByCandidate.map(function(cid){
        return (
          <tr className='small'>
          <td>{cid.candidate.name + ' ' + cid.candidate.surname}</td>
          <td>{cid.candidate.partijosPavadinimas}</td>
          <td>{cid.votes}</td>
          <td>{Math.round(cid.votes/self.props.results.valid*100,2)}%</td>
          <td>{Math.round(cid.votes/self.props.results.voters*100,2)}%</td>
          </tr>
        );
      });
    }
    return (
      <div>
      <div className="container"><h1>{this.props.district.name} rinkimų apylinkė</h1></div>
      <div id="exTab1" className="container">
      <ul  className="nav nav-pills">
            <li className="active">
              <a  href="#1a" data-toggle="tab">Apylinkės Informacija</a>
            </li>
            <li><a onClick={this.loadPieSingle} href="#2a" data-toggle="tab">Vienmandatės rezultatai</a>
            </li>
            <li><a href="#3a" data-toggle="tab">Daugiamandatės rezultaitai</a>
            </li>
          </ul>

            <div className="tab-content clearfix">
              <div className="tab-pane active" id="1a">
                <h3>Contents background color is the same for the tab</h3>
              </div>

              <div className="tab-pane" id="2a">
                <h3>Rinkėjų aktyvumas: {this.props.results.valid} balsavusių iš {this.props.results.voters}, {Math.round(this.props.results.valid / this.props.results.voters *100,2)}%</h3>
                <h3>Sugadintų biuletenių skaičius: {this.props.results.spoiledSingle}</h3>
                <h5>{this.props.singleText}</h5>
                <div className='col-md-7'>
                <table className="table table-striped">
                    <thead>
                      <tr>
                        <th className='col-md-4'>Kandidatas</th>
                        <th className='col-md-3'>Partija</th>
                        <th className='col-md-2'>Balsai</th>
                        <th className='col-md-2'>% nuo galiojančių</th>
                        <th className='col-md-1'>% nuo visų</th>
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
