function getDistrictInfo(self,id) {
  axios
  .get('/district/' + id)
  .then(function(response){
    self.setState({
      district :  response.data,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('DistrictResultsContainer.componentWillMount.axios', err);
  });
}
function getResults(self,id) {
  axios
  .get('/results/district/' + id)
  .then(function(response){
    self.setState({
      results :  response.data,
    });
  })
  .catch(function(err){
    console.error('DistrictResultsContainer.componentWillMount.axios', err);
  });
}
function formSingleText(self){
  var singleVotes = self.state.results.votesByCandidate;
  var winner = 'Daugiausiai balsų surinko ' + singleVotes[0].candidate.name + ' ' + singleVotes[0].candidate.surname + ' surinkęs ' + singleVotes[0].votes + ' balsų.';
  var loser = ' Antras ' + singleVotes[1].candidate.name + ' ' + singleVotes[1].candidate.surname + ', gavęs ' + (singleVotes[0].votes - singleVotes[1].votes) + ' balsų mažiau.';
  return winner + loser;
}
var DistrictResultsContainer  = React.createClass({
  getInitialState: function() {
    return {
      results:[],
      district:[],
      isLoading:true,
      did : 1,
    };
  },
  componentDidMount: function() {
    getResults(this,this.props.params.id);
    getDistrictInfo(this,this.props.params.id);

  },
  componentWillReceiveProps: function(nextProps) {
    getResults(this, nextProps.params.id);
    getDistrictInfo(this, nextProps.params.id);
  },
  render: function() {
    if (this.state.isLoading) {
      return <div><img src='images/loading.gif'/></div>;
    }
    return (
      <DistrictResults
        canvasBar={<canvas id='barC'></canvas>}
        results={this.state.results}
        district={this.state.district}
        singleText = {formSingleText(this)}

      />
    );
  }

});

window.DistrictResultsContainer = DistrictResultsContainer;
