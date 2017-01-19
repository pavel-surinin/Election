var PartyDetailViewContainer = React.createClass({
  getInitialState: function() {
    return {
      partyDetails : null,
      isLoading : true,
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
      .get('/party/' + this.props.params.id)
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
        <PartyDetailViewComponent partyDetails={this.state.partyDetails}/>
      );
    }
  }
  });

window.PartyDetailViewContainer = PartyDetailViewContainer;
