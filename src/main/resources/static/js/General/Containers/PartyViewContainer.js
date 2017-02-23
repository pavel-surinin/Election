function getParties(self) {
  axios
  .get('/party')
  .then(function(response){
    EventEmitter.publish({ eventType: 'LogCounty' });
    self.setState({
      partyList :  response.data,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('PartyContainer.componentWillMount.axios.get.party', err);
  });
}

var PartyViewContainer = React.createClass({
  getInitialState: function() {
    return {
      partyList : [],
      isLoading : true,
    };
  },

  componentWillMount: function() {
    getParties(this);
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    }
      return (
        <PartyViewComponent
          partyList={this.state.partyList}

        />
      );
    }
});

window.PartyViewContainer = PartyViewContainer;
