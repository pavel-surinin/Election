function getPartyById(self,id) {
  axios
  .get('/party/' + id)
  .then(function(response){
    self.setState({
      partyDetails :  response.data,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('PartyDetailContainer.getPartyById.axios.get.party/:id', err);
  });
}
function getGeneralResults(self) {
  axios
  .get('/results/general/')
  .then(function(response){
    self.setState({
      generalResults :  response.data,
    });
  })
  .catch(function(err){
    console.error('PartyDetailContainer.getGeneralResults.axios', err);
  });
}
var PartyDetailContainer = React.createClass({
  getInitialState: function() {
    return {
      id: '',
      partyDetails : [],
      generalResults : [],
      isLoading : true,
    };
  },
  componentWillMount: function() {
    getPartyById(this, this.props.params.id);
    getGeneralResults(this);
  },


  render: function() {
    if (this.state.isLoading) {
      return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
    } else {
      return (
      <PartyDetailComponent
        partyDetails={this.state.partyDetails}
        generalResults={this.state.generalResults}
        pid={this.props.params.id}
      />
      );
    }
  }
});

window.PartyDetailContainer = PartyDetailContainer;
