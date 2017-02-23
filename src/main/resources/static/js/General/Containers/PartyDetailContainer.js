function getPartyById(self,id) {
  axios
  .get('/party/' + id)
  .then(function(response){
    console.log(response.data);
    self.setState({
      partyDetails :  response.data,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('PartyDetailViewContainer.componentWillMount.axios.get.party/:id', err);
  });
}

var PartyDetailContainer = React.createClass({
  getInitialState: function() {
    return {
      id: '',
      partyDetails : null,
      isLoading : true,
    };
  },
  componentWillMount: function() {
    getPartyById(this, this.props.params.id);
  },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
        return (
      <PartyComponent
        partyDetails={this.state.partyDetails}

      />
      );
    }
  }
});

window.PartyDetailContainer = PartyDetailContainer;
