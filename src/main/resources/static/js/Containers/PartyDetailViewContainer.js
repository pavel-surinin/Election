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

var PartyDetailViewContainer = React.createClass({
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
  onHandleDeleteClick : function(event){
    var self = this;
    event.preventDefault();
    axios
      .delete('/candidate/party/'+ this.props.params.id)
      .then(function(response){

        getPartyById(self, self.props.params.id);
      })
      .catch(function(err){
        console.error('PartyDetailViewContainer.onHandleDeleteClick.axios', err);
      });
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='https://upload.wikimedia.org/wikipedia/commons/b/b1/Loading_icon.gif'/>
        </div>
      );
    } else {
      console.log(this.state.partyDetails);
      return (
        <PartyDetailViewComponent
        partyDetails={this.state.partyDetails}
        onHandleDeleteClick={this.onHandleDeleteClick}
        partyId={this.state.id}
        />
      );
    }
  }
  });

window.PartyDetailViewContainer = PartyDetailViewContainer;
