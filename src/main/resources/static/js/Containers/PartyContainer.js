var PartyContainer = React.createClass({
  getInitialState: function() {
    return {
      partyList : [],
      isLoading : true,
      succesCreateText : '',
    };
  },
  componentWillMount: function() {
    console.log(this.props);
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    var self = this;
    axios
      .get('/party')
      .then(function(response){
        self.setState({
          partyList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('PartyContainer.componentWillMount.axios.get.party', err);
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
      console.log(this.state.partyList);
      return (
        <PartyListViewComponent
          partyList={this.state.partyList}
          succesCreateText={this.state.succesCreateText}
        />
      );
    }
  }
});

window.PartyContainer = PartyContainer;
