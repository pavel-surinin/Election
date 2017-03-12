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
    document.title='Partijos Rinkimai 2017';
    if (this.state.isLoading) {
      return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
    }
      return (
        <PartyViewComponent
          partyList={this.state.partyList}

        />
      );
    }
});

window.PartyViewContainer = PartyViewContainer;
