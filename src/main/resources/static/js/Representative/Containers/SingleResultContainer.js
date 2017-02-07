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
      if (response.data.resultMultiRegistered) {
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
  render: function() {
    return (
      <div>
        <SingleResultComponent />
      </div>
    );
  }

});

window.SingleResultContainer = SingleResultContainer;
