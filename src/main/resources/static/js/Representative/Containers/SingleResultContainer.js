function getCandidates(self,district) {
  axios
    .get('candidate/' + district + '/district')
    .then(function(response){
      self.setState({
        candidatesList :  response.data,
      });
    })
    .catch(function(err){
      console.error('SingleResultContainer.componentWillMount.axios.get.party', err);
    });
}
function getDistrict(self,id) {
  axios
    .get('/district/' + id)
    .then(function(response){
      self.setState({
        districtInfo :  response.data,
        isLoading : false,
      });
      if (response.data.resultSingleRegistered) {
        self.setState({isVotesRegistered : true});
      }
    })
    .catch(function(err){
      console.error('SingleResultContainer.componentWillMount.axios.get.district', err);
    });
}

var SingleResultContainer = React.createClass({
  getInitialState: function() {
    return {
      districtId : 5,
      candidatesList : [],
      districtInfo : [],
      isLoading : true,
      isVotesRegistered : false,
    };
  },
  componentWillMount: function() {
    getCandidates(this,this.state.districtId);
    getDistrict(this,this.state.districtId);
  },
  registerVotes :function(id,votes){
    console.log(id,votes);
  },
  render: function() {
    console.log(this.state);
    if (this.state.isLoading) {
      return <div><img src='/images/loading.gif'/></div>;
    }
    if (this.state.isVotesRegistered) {
      return(
        <div>
          {alerts.showSucces('Jūsų apylinkės balsai užregistruoti')}
        </div>
      );
    } else {
      return (
        <SingleResultComponent
          list={this.state.candidatesList}
          registerVotes={this.registerVotes}
        />
      );
    }
  }
});


window.SingleResultContainer = SingleResultContainer;
