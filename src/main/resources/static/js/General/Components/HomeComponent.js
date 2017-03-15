function getResults(self) {
  axios
  .get('results/general')
  .then(function(response){
    self.setState({results : response.data});
  });
}
function getUnitsCounts(self) {
  axios
  .get('users/admin-info')
  .then(function(response){
    self.setState({count : response.data});
  });
}
function getNumbersForBars(self) {
  var res = self.state.results;
  return{
    disPC : ((res.districtsVoted / res.districtsCount) * 100).toFixed(2),
    votersActivity : (((res.votesCount + res.spoiledMulti + res.spoiledSingle) / res.votersCount)*100).toFixed(2),
  };
}
var HomeComponent = React.createClass({
  getInitialState: function() {
    return {
      results:[],
      count:[],
    };
  },
  componentWillMount: function() {
    getResults(this);
    getUnitsCounts(this);
  },
  render: function() {
    document.title='Rinkimai 2017';
    var resCounts = getNumbersForBars(this);
    var res = this.state.results;
    return (
      <div>
        <div className="container">
          <h1 className='yellow'>Rinkimai 2017</h1>
          <div id="exTab1" className="container shadow">
            <div className="tab-content clearfix">
              <div className="col-xs-12 col-sm-6 col-md-6">
                <h2>Rinkėjų aktyvumas</h2>
                <div className="progress">
                  <div className="progress-bar progress-bar-striped active" role="progressbar"
                  aria-valuenow={resCounts.votersActivity} aria-valuemin="0" aria-valuemax="100" style={{width: resCounts.votersActivity + '%'}}>
                    {resCounts.votersActivity  + '%'}
                  </div>
                </div>
              </div>
              <div className="col-xs-12 col-sm-6 col-md-6">
                <h2>Prabalsavo apylinkių {res.districtsVoted} iš {res.districtsCount}</h2>
                <div className="progress">
                  <div className="progress-bar progress-bar-striped active" role="progressbar"
                  aria-valuenow={resCounts.disPC} aria-valuemin="0" aria-valuemax="100" style={{width: resCounts.disPC + '%'}}>
                    {res.districtsVoted}
                  </div>
                </div>
              </div>
                <div className="col-xs-12 col-sm-6 col-md-4 clearfix">
                  <h2>Rezultaitai CSV</h2>
                  <p><a target="_blank" href="results/csv/single">Vienmandačių rezultaitai</a> </p>
                  <p><a target="_blank" href="results/csv/multi/winners">Daigiamandačių laimėtojai</a></p>
                  <p><a target="_blank" href="results/csv/multi/votes">Daugiamandačių balsai</a></p>
                  <p><a target="_blank" href="results/csv/mandates">Kiek partijos gavo mandatų daugiamandatėse</a></p>
                  <p><a target="_blank" href="results/csv/mandates/general">Kiek partijos gavo mandatų</a></p>
                </div>

                <div className="col-xs-12 col-sm-6 col-md-4 ">
                  <h2>Web serviso api</h2>
                  <p><a target="_blank" href="results/general">Rezultaitai (json)</a> </p>
                </div>

                <div className="col-xs-12 col-sm-6 col-md-4">
                  <h2>Rinkimų eiga</h2>
                  <p>Prabalsavo rinkėjų {res.votesCount} iš {res.votersCount}</p>
                  <p>Prabalsavo apylinkių {res.districtVoted} iš {res.districtCount}</p>
                  <p>Sugadinta balsų vienmandatėje {res.spoiledSingle}</p>
                  <p>Sugadinta balsų daugiamandatėje {res.spoiledMulti}</p>
                  <p>Užregitruota Apygardų: {this.state.count[0]}</p>
                  <p>Užregitruota Apylinkių: {this.state.count[1]}</p>
                  <p>Užregitruota Partijų: {this.state.count[3]} </p>
                  <p>Užregitruota Kandidatų: {this.state.count[4]}</p>
                </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

});

window.HomeComponent = HomeComponent;
